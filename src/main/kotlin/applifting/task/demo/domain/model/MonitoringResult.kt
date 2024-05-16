package applifting.task.demo.domain.model

import java.time.OffsetDateTime
import java.util.UUID

data class MonitoringResult(
    val id: UUID = UUID.randomUUID(),
    val dateOfCheck: OffsetDateTime,
    val httpStatusCode: Int,
    val payload: String? = null,
    val monitoredEndpoint: MonitoredEndpoint,
)
