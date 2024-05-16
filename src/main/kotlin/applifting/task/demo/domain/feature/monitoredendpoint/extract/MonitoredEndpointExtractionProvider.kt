package applifting.task.demo.domain.feature.monitoredendpoint.extract

import applifting.task.demo.domain.model.MonitoredEndpoint
import java.util.UUID

interface MonitoredEndpointExtractionProvider {
    fun getMonitoredEndpointsByOwnerId(ownerId: UUID): List<MonitoredEndpoint>

    fun getMonitoredEndpointById(id: UUID): MonitoredEndpoint?

    fun getAllMonitoredEndpoints(): List<MonitoredEndpoint>
}
