package applifting.task.demo.domain.feature.monitoredendpoint.extract

import applifting.task.demo.domain.model.MonitoredEndpoint
import org.springframework.http.ResponseEntity
import java.util.UUID

interface MonitoredEndpointExtractor {
    fun getMonitoredEndpoints(token: UUID): ResponseEntity<List<MonitoredEndpoint>>

    fun getMonitoredEndpoint(envelope: MonitoredEndpointExtractionEnvelope): ResponseEntity<MonitoredEndpoint>

    fun getAllMonitoredEndpoints(): List<MonitoredEndpoint>
}
