package applifting.task.demo.domain.feature.monitoredendpoint.checkdateset

import java.time.OffsetDateTime
import java.time.OffsetDateTime.now
import java.util.UUID

interface MonitoredEndpointLastCheckDateSetterProvider {
    fun updateDateOfLastCheckById(
        id: UUID,
        dateOfLastCheck: OffsetDateTime = now(),
    )
}
