package applifting.task.demo.domain.model

import java.time.OffsetDateTime
import java.util.UUID

data class MonitoredEndpoint(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val url: String,
    val dateOfLastUpdate: OffsetDateTime,
    val dateOfLastCheck: OffsetDateTime? = null,
    val monitoredInterval: Int,
    val owner: User,
)
