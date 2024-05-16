package applifting.task.demo.domain.feature.monitoringresults.extract

import applifting.task.demo.domain.model.MonitoringResult
import applifting.task.demo.shared.logEndpointNotFound
import applifting.task.demo.shared.logForbidden
import applifting.task.demo.shared.logUserNotFound
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus.FORBIDDEN
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Component
class DefaultMonitoringResultsExtractor(
    private val provider: MonitoringResultsExtractionProvider,
    private val userExtractor: UserExtractionProvider,
    private val endpointChecker: MonitoredEndpointCheckProvider,
) : MonitoringResultsExtractor {
    private val logger = LoggerFactory.getLogger(DefaultMonitoringResultsExtractor::class.java)

    override fun getLastTenMonitoringResultsByEndpointId(
        envelope: MonitoringResultExtractionEnvelope,
    ): ResponseEntity<List<MonitoringResult>> {
        val user = userExtractor.getUserByAssesToken(envelope.token)
        return when {
            user == null -> {
                logger.logUserNotFound(envelope.token)
                ResponseEntity.notFound().build()
            }

            !endpointChecker.checkIfCanBeAccessedByUserIfEndpointExists(endpointId = envelope.endpointId, userId = user.id) -> {
                logger.logForbidden(envelope.endpointId)
                ResponseEntity.status(FORBIDDEN).build()
            }
            !endpointChecker.checkIfEndpointExists(endpointId = envelope.endpointId) -> {
                logger.logEndpointNotFound(envelope.endpointId)
                ResponseEntity.notFound().build()
            }
            else -> {
                logger.info("Successfully extracted monitoring results for ${envelope.endpointId}")
                ResponseEntity.ok(provider.getLastTenMonitoringResultsByEndpointId(envelope.endpointId))
            }
        }
    }
}
