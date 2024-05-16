package applifting.task.demo.domain.feature.monitoredendpoint.checkdateset

import java.util.UUID

interface MonitoredEndpointLastCheckDateSetter {
    fun updateDateOfLastCheckById(id: UUID)
}
