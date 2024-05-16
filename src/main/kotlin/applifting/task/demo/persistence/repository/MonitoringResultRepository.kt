package applifting.task.demo.persistence.repository

import applifting.task.demo.persistence.model.MonitoringResult
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface MonitoringResultRepository : CrudRepository<MonitoringResult, UUID> {
    fun findTop10ByMonitoredEndpointIdOrderByDateOfCheckDesc(monitoredEndpointId: UUID): List<MonitoringResult>
}
