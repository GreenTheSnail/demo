package applifting.task.demo.domain.feature.monitoredendpoint.extract

import applifting.task.demo.domain.model.MonitoredEndpoint
import applifting.task.demo.shared.logEndpointNotFound
import applifting.task.demo.shared.logForbidden
import applifting.task.demo.shared.logUserNotFound
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus.FORBIDDEN
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class DefaultMonitoredEndpointExtractor(
    private val provider: MonitoredEndpointExtractionProvider,
    private val userProvider: UserExtractionProvider,
    private val endpointChecker: MonitoredEndpointCheckProvider,
) : MonitoredEndpointExtractor {
    private val logger = LoggerFactory.getLogger(DefaultMonitoredEndpointExtractor::class.java)

    override fun getMonitoredEndpoints(token: UUID): ResponseEntity<List<MonitoredEndpoint>> {
        val user = userProvider.getUserByAssesToken(token)
        return when {
            user == null -> {
                logger.logUserNotFound(token)
                ResponseEntity.notFound().build()
            }
            else -> {
                logger.info("Successfully extracted monitored endpoints")
                ResponseEntity.ok(provider.getMonitoredEndpointsByOwnerId(user.id))
            }
        }
    }

    override fun getMonitoredEndpoint(envelope: MonitoredEndpointExtractionEnvelope): ResponseEntity<MonitoredEndpoint> {
        val user = userProvider.getUserByAssesToken(envelope.userToken)
        val endpoint = provider.getMonitoredEndpointById(envelope.endpointId)
        return when {
            user == null -> {
                logger.logUserNotFound(envelope.userToken)
                ResponseEntity.notFound().build()
            }
            endpoint == null -> {
                logger.logEndpointNotFound(envelope.endpointId)
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
                ResponseEntity.ok(endpoint)
            }
        }
    }

    override fun getAllMonitoredEndpoints() = provider.getAllMonitoredEndpoints()
}
