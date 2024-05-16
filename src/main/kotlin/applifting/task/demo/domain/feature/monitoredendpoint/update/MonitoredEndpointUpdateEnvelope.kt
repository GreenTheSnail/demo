package applifting.task.demo.domain.feature.monitoredendpoint.update

import applifting.task.demo.domain.model.User
import java.util.UUID

data class MonitoredEndpointUpdateEnvelope(
    val id: UUID,
    val name: String? = null,
    val url: String? = null,
    val monitoredInterval: Int? = null,
    val ownerId: UUID? = null,
)

data class MonitoredEndpointUpdateEnvelopeWithToken(
    val id: UUID,
    val userToken: UUID,
    val name: String? = null,
    val url: String? = null,
    val monitoredInterval: Int? = null,
    val ownerId: UUID? = null,
)

data class MonitoredEndpointUpdateEnvelopeExtended(
    val id: UUID,
    val name: String? = null,
    val url: String? = null,
    val monitoredInterval: Int? = null,
    val owner: User? = null,
)
