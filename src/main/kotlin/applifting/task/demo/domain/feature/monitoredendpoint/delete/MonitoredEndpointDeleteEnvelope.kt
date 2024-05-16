package applifting.task.demo.domain.feature.monitoredendpoint.delete

import java.util.UUID

data class MonitoredEndpointDeleteEnvelope(
    val endpointId: UUID,
    val userToken: UUID,
)
