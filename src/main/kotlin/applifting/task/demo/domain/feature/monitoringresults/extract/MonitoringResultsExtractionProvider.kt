package applifting.task.demo.domain.feature.monitoringresults.extract

import applifting.task.demo.domain.model.MonitoringResult
import java.util.UUID

interface MonitoringResultsExtractionProvider {
    fun getLastTenMonitoringResultsByEndpointId(id: UUID): List<MonitoringResult>
}
