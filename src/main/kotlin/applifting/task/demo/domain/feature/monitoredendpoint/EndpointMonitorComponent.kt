package applifting.task.demo.domain.feature.monitoredendpoint

import applifting.task.demo.domain.feature.monitoredendpoint.checkdateset.MonitoredEndpointLastCheckDateSetter
import applifting.task.demo.domain.feature.monitoredendpoint.extract.MonitoredEndpointExtractor
import applifting.task.demo.domain.feature.monitoringresults.create.MonitoringResultCreator
import applifting.task.demo.domain.model.MonitoredEndpoint
import applifting.task.demo.domain.model.MonitoringResult
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import java.time.OffsetDateTime
import java.util.UUID

@Component
class EndpointMonitorComponent(
    private val restTemplate: RestTemplate,
    private val endpointsExtractor: MonitoredEndpointExtractor,
    private val endpointsLastCheckDateSetter: MonitoredEndpointLastCheckDateSetter,
    private val resultCreator: MonitoringResultCreator,
) {
    private val logger = LoggerFactory.getLogger(EndpointMonitorComponent::class.java)
    private val monitoringJobs: MutableMap<UUID, Job> = mutableMapOf()

    fun initializeMonitorEndpoints() {
        val endpoints = endpointsExtractor.getAllMonitoredEndpoints()
        endpoints.forEach { endpoint ->
            if (!monitoringJobs.containsKey(endpoint.id)) {
                val interval = endpoint.monitoredInterval * 1000
                val job = scheduleMonitoringTask(endpoint, interval.toLong())
                monitoringJobs[endpoint.id] = job
            }
        }
    }

    fun addMonitoredEndpoint(endpoint: MonitoredEndpoint) {
        if (!monitoringJobs.containsKey(endpoint.id)) {
            val interval = endpoint.monitoredInterval * 1000
            val job = scheduleMonitoringTask(endpoint, interval.toLong())
            monitoringJobs[endpoint.id] = job
        }
    }

    fun removeMonitoredEndpoint(endpointId: UUID) {
        stopMonitoringTask(endpointId)
    }

    fun updateMonitoredEndpoint(endpoint: MonitoredEndpoint) {
        stopMonitoringTask(endpoint.id)
        val interval = endpoint.monitoredInterval * 1000
        val job = scheduleMonitoringTask(endpoint, interval.toLong())
        monitoringJobs[endpoint.id] = job
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun scheduleMonitoringTask(
        endpoint: MonitoredEndpoint,
        interval: Long,
    ): Job {
        return GlobalScope.launch {
            while (isActive) {
                monitorEndpoint(endpoint)
                delay(interval)
            }
        }
    }

    private fun monitorEndpoint(endpoint: MonitoredEndpoint) {
        try {
            val response = restTemplate.getForEntity(endpoint.url, String::class.java)
            val statusCode = response.statusCode.value()
            val responseBody = response.body
            val monitoringResult =
                MonitoringResult(
                    dateOfCheck = OffsetDateTime.now(),
                    httpStatusCode = statusCode,
                    payload = responseBody,
                    monitoredEndpoint = endpoint,
                )
            resultCreator.saveMonitoringResult(monitoringResult)
            logger.info(
                "Creating MonitoringResult for endpoint ${endpoint.name} with status code $statusCode and response body: $responseBody.",
            )
        } catch (e: Exception) {
            val monitoringResult =
                when {
                    e is HttpClientErrorException -> {
                        logger.info("Creating MonitoringResult for endpoint ${endpoint.name} with status code ${e.statusCode.value()}")
                        MonitoringResult(
                            dateOfCheck = OffsetDateTime.now(),
                            httpStatusCode = e.statusCode.value(),
                            payload = e.message,
                            monitoredEndpoint = endpoint,
                        )
                    }
                    else -> {
                        logger.info("Error monitoring endpoint ${endpoint.url}: ${e.message}")
                        MonitoringResult(
                            dateOfCheck = OffsetDateTime.now(),
                            httpStatusCode = -1,
                            payload = e.message,
                            monitoredEndpoint = endpoint,
                        )
                    }
                }

            resultCreator.saveMonitoringResult(monitoringResult)
        }
        endpointsLastCheckDateSetter.updateDateOfLastCheckById(endpoint.id)
    }

    fun stopMonitoringTask(endpointId: UUID) {
        monitoringJobs[endpointId]?.cancel()
        monitoringJobs.remove(endpointId)
    }
}
