package applifting.task.demo.controller

import applifting.task.demo.controller.mapping.convertToUUID
import applifting.task.demo.domain.feature.monitoringresults.extract.MonitoringResultExtractionEnvelope
import applifting.task.demo.domain.feature.monitoringresults.extract.MonitoringResultsExtractor
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/monitored-results")
class MonitoringResultsController(
    private val extractor: MonitoringResultsExtractor,
) {
    @GetMapping("/{endpointId}")
    fun getMonitoringResultsByEndpointId(
        @RequestHeader("Authorization") token: String,
        @PathVariable endpointId: UUID,
    ) = extractor.getLastTenMonitoringResultsByEndpointId(
        MonitoringResultExtractionEnvelope(
            endpointId = endpointId,
            token = token.convertToUUID(),
        ),
    )
}
