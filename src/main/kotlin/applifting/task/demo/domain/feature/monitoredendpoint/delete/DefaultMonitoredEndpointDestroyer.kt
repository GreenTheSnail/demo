package applifting.task.demo.domain.feature.monitoredendpoint.delete

import applifting.task.demo.domain.feature.monitoredendpoint.EndpointMonitorComponent
import applifting.task.demo.shared.logEndpointNotFound
import applifting.task.demo.shared.logForbidden
import applifting.task.demo.shared.logUserNotFound
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus.FORBIDDEN
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Component
class DefaultMonitoredEndpointDestroyer(
    private val provider: MonitoredEndpointDeletingProvider,
    private val userProvider: UserExtractionProvider,
    private val monitor: EndpointMonitorComponent,
    private val endpointChecker: MonitoredEndpointCheckProvider,
) : MonitoredEndpointDestroyer {
    private val logger = LoggerFactory.getLogger(DefaultMonitoredEndpointDestroyer::class.java)

    override fun deleteById(envelope: MonitoredEndpointDeleteEnvelope): ResponseEntity<Unit> {
        val user = userProvider.getUserByAssesToken(envelope.userToken)
        return when {
            user == null -> {
                logger.logUserNotFound(envelope.userToken)
                ResponseEntity.notFound().build()
            }
            !endpointChecker.checkIfCanBeAccessedByUserIfEndpointExists(
                endpointId = envelope.endpointId,
                userId = user.id,
            ) -> {
                logger.logForbidden(envelope.userToken)
                ResponseEntity.status(FORBIDDEN).build()
            }
            else -> {
                val result = provider.deleteById(envelope.endpointId)
                if (result == null) {
                    logger.logEndpointNotFound(envelope.endpointId)
                    ResponseEntity.notFound().build()
                } else {
                    monitor.removeMonitoredEndpoint(envelope.endpointId)
                    logger.info("Successfully removed endpoint with ${envelope.endpointId}")
                    ResponseEntity.ok().build()
                }
            }
        }
    }
}
