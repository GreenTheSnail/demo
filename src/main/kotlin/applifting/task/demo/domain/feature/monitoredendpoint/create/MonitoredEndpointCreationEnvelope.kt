package applifting.task.demo.domain.feature.monitoredendpoint.create

import applifting.task.demo.domain.model.User
import java.util.UUID

data class MonitoredEndpointCreationEnvelope(
    val name: String,
    val url: String,
    val monitoredInterval: Int,
)

data class MonitoredEndpointCreationEnvelopeWithToken(
    val name: String,
    val url: String,
    val monitoredInterval: Int,
    val token: UUID,
)

data class MonitoredEndpointCreationEnvelopeExtended(
    val name: String,
    val url: String,
    val monitoredInterval: Int,
    val owner: User,
)
