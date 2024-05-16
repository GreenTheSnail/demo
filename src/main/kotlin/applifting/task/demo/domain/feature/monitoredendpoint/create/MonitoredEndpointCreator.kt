package applifting.task.demo.domain.feature.monitoredendpoint.create

import applifting.task.demo.domain.model.MonitoredEndpoint
import org.springframework.http.ResponseEntity

interface MonitoredEndpointCreator {
    fun createMonitoredEndpoint(envelope: MonitoredEndpointCreationEnvelopeWithToken): ResponseEntity<MonitoredEndpoint>
}
