package applifting.task.demo.persistence.model

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import java.time.OffsetDateTime
import java.util.UUID
import applifting.task.demo.domain.model.MonitoredEndpoint as MonitoredEndpointDomainObject

@Entity(name = "monitored_endpoints")
data class MonitoredEndpoint(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID,
    val name: String,
    val url: String,
    val dateOfLastUpdate: OffsetDateTime,
    val dateOfLastCheck: OffsetDateTime? = null,
    val monitoredInterval: Int,
    @ManyToOne(fetch = FetchType.LAZY)
    val owner: User,
)

fun MonitoredEndpoint.toDomain() =
    MonitoredEndpointDomainObject(
        id = id,
        name = name,
        url = url,
        dateOfLastUpdate = dateOfLastUpdate,
        dateOfLastCheck = dateOfLastCheck,
        monitoredInterval = monitoredInterval,
        owner = owner.toDomain(),
    )

fun MonitoredEndpointDomainObject.toPersistence() =
    MonitoredEndpoint(
        id = id,
        name = name,
        url = url,
        dateOfLastUpdate = dateOfLastUpdate,
        dateOfLastCheck = dateOfLastCheck,
        monitoredInterval = monitoredInterval,
        owner = owner.toPersistence(),
    )
