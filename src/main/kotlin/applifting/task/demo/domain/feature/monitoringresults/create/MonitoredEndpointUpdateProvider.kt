package applifting.task.demo.domain.feature.monitoringresults.create

import java.util.UUID

interface MonitoredEndpointUpdateProvider {
    fun updateDateOfLastCheckById(id: UUID)
}
