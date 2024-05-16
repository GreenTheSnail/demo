package applifting.task.demo.domain.feature.monitoredendpoint.update

import applifting.task.demo.domain.model.MonitoredEndpoint
import org.springframework.http.ResponseEntity

interface MonitoredEndpointUpdater {
    fun updateMonitoredEndpoint(envelope: MonitoredEndpointUpdateEnvelopeWithToken): ResponseEntity<MonitoredEndpoint>
}
