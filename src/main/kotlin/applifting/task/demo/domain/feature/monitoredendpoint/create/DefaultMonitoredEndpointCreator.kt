package applifting.task.demo.domain.feature.monitoredendpoint.create

import applifting.task.demo.domain.feature.monitoredendpoint.EndpointMonitorComponent
import applifting.task.demo.domain.model.MonitoredEndpoint
import applifting.task.demo.domain.model.User
import applifting.task.demo.shared.isValidInterval
import applifting.task.demo.shared.isValidUrl
import applifting.task.demo.shared.logForbidden
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus.FORBIDDEN
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Component
class DefaultMonitoredEndpointCreator(
    private val provider: MonitoredEndpointCreationProvider,
    private val userProvider: UserExtractionProvider,
    private val monitor: EndpointMonitorComponent,
) : MonitoredEndpointCreator {
    private val logger = LoggerFactory.getLogger(DefaultMonitoredEndpointCreator::class.java)

    override fun createMonitoredEndpoint(envelope: MonitoredEndpointCreationEnvelopeWithToken): ResponseEntity<MonitoredEndpoint> {
        val user = userProvider.getUserByAssesToken(envelope.token)
        return when {
            user == null -> {
                logger.logForbidden(envelope.token)
                ResponseEntity.status(FORBIDDEN).build()
            }
            !envelope.isValid() -> ResponseEntity.badRequest().build()
            else ->
                ResponseEntity.ok(
                    provider.createMonitoredEndpoint(envelope.extendByOwner(user)).also {
                        monitor.addMonitoredEndpoint(it)
                    },
                )
        }
    }

    private fun MonitoredEndpointCreationEnvelopeWithToken.isValid() =
        when {
            name.isBlank() -> false.also { logger.error("Name is blank") }
            url.isBlank() -> false.also { logger.error("Url is blank") }
            !url.isValidUrl() -> false.also { logger.error("Url not valid") }
            monitoredInterval.isValidInterval() -> false.also { logger.error("Interval can not be zero or less") }
            else -> true
        }
}

private fun MonitoredEndpointCreationEnvelopeWithToken.extendByOwner(owner: User) =
    MonitoredEndpointCreationEnvelopeExtended(
        name = name,
        url = url,
        monitoredInterval = monitoredInterval,
        owner = owner,
    )
