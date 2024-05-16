package applifting.task.demo.persistence.repository

import applifting.task.demo.persistence.model.MonitoredEndpoint
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface MonitoredEndpointRepository : CrudRepository<MonitoredEndpoint, UUID> {
    fun getMonitoredEndpointById(id: UUID): MonitoredEndpoint?

    fun getMonitoredEndpointsByOwnerId(ownerId: UUID): List<MonitoredEndpoint>
}
