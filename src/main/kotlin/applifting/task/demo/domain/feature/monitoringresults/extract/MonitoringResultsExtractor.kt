package applifting.task.demo.domain.feature.monitoringresults.extract

import applifting.task.demo.domain.model.MonitoringResult
import org.springframework.http.ResponseEntity

interface MonitoringResultsExtractor {
    fun getLastTenMonitoringResultsByEndpointId(envelope: MonitoringResultExtractionEnvelope): ResponseEntity<List<MonitoringResult>>
}
