package applifting.task.demo.persistence.model

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import java.time.OffsetDateTime
import java.util.UUID
import applifting.task.demo.domain.model.MonitoringResult as MonitoringResultDomainObject

@Entity(name = "monitoring_results")
data class MonitoringResult(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID,
    val dateOfCheck: OffsetDateTime,
    val httpStatusCode: Int,
    val payload: String? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    val monitoredEndpoint: MonitoredEndpoint,
)

fun MonitoringResult.toDomain() =
    MonitoringResultDomainObject(
        id = id,
        dateOfCheck = dateOfCheck,
        httpStatusCode = httpStatusCode,
        payload = payload,
        monitoredEndpoint = monitoredEndpoint.toDomain(),
    )

fun MonitoringResultDomainObject.toPersistence() =
    MonitoringResult(
        id = id,
        dateOfCheck = dateOfCheck,
        httpStatusCode = httpStatusCode,
        payload = payload,
        monitoredEndpoint = monitoredEndpoint.toPersistence(),
    )
