package applifting.task.demo.domain.feature.monitoredendpoint.update

import applifting.task.demo.domain.feature.monitoredendpoint.EndpointMonitorComponent
import applifting.task.demo.domain.model.MonitoredEndpoint
import applifting.task.demo.domain.model.User
import applifting.task.demo.shared.isValidInterval
import applifting.task.demo.shared.isValidUrl
import applifting.task.demo.shared.logEndpointNotFound
import applifting.task.demo.shared.logForbidden
import applifting.task.demo.shared.logUserNotFound
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus.FORBIDDEN
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Component
class DefaultMonitoredEndpointUpdater(
    private val provider: MonitoredEndpointUpdateProvider,
    private val userProvider: UserExtractionProvider,
    private val monitor: EndpointMonitorComponent,
    private val endpointChecker: MonitoredEndpointCheckProvider,
) : MonitoredEndpointUpdater {
    private val logger = LoggerFactory.getLogger(DefaultMonitoredEndpointUpdater::class.java)

    override fun updateMonitoredEndpoint(envelope: MonitoredEndpointUpdateEnvelopeWithToken): ResponseEntity<MonitoredEndpoint> {
        val user = userProvider.getUserByAssesToken(envelope.userToken)
        val newPossibleUser = envelope.ownerId?.let { userProvider.getUserById(it) }
        return when {
            user == null -> {
                logger.logUserNotFound(envelope.userToken)
                ResponseEntity.notFound().build()
            }
            !endpointChecker.checkIfCanBeAccessedByUserIfEndpointExists(
                endpointId = envelope.id,
                userId = user.id,
            ) -> {
                logger.logForbidden(envelope.userToken)
                ResponseEntity.status(FORBIDDEN).build()
            }
            !envelope.isValid(newPossibleUser) -> ResponseEntity.badRequest().build()
            else -> {
                val endpoint =
                    provider.updateMonitoredEndpoint(
                        envelope.extendByOwner(newPossibleUser),
                    )
                if (endpoint == null) {
                    logger.logEndpointNotFound(envelope.id)
                    ResponseEntity.notFound().build()
                } else {
                    monitor.updateMonitoredEndpoint(endpoint)
                    ResponseEntity.ok(endpoint)
                }
            }
        }
    }

    private fun MonitoredEndpointUpdateEnvelopeWithToken.isValid(newPossibleUser: User?) =
        when {
            name.notNullAndFulfill(String::isBlank) -> false.also { logger.error("Name is blank") }
            url.notNullAndFulfill(String::isBlank) -> false.also { logger.error("Url is blank") }
            !url.notNullAndFulfill(String::isValidUrl) -> false.also { logger.error("Url not valid") }
            monitoredInterval.notNullAndFulfill(Int::isValidInterval) -> false.also { logger.error("Interval can not be zero or less") }
            ownerId != null && newPossibleUser == null -> false.also { logger.error("New owner is not found") }
            else -> true
        }
}

private fun <T> T?.notNullAndFulfill(validator: T.() -> Boolean) = this != null && validator()

fun MonitoredEndpointUpdateEnvelopeWithToken.extendByOwner(owner: User?) =
    MonitoredEndpointUpdateEnvelopeExtended(
        id = id,
        name = name,
        url = url,
        monitoredInterval = monitoredInterval,
        owner = owner,
    )
