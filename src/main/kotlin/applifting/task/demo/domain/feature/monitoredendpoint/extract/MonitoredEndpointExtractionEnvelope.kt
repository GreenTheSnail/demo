package applifting.task.demo.domain.feature.monitoredendpoint.extract

import java.util.UUID

data class MonitoredEndpointExtractionEnvelope(
    val endpointId: UUID,
    val userToken: UUID,
)
