package applifting.task.demo.domain.feature.monitoringresults.extract

import java.util.UUID

data class MonitoringResultExtractionEnvelope(
    val endpointId: UUID,
    val token: UUID,
)
