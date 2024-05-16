package applifting.task.demo.domain.feature.monitoredendpoint.delete

import java.util.UUID

interface MonitoredEndpointDeletingProvider {
    fun deleteById(id: UUID): Unit?
}
