package applifting.task.demo.domain.feature.monitoredendpoint.delete

import org.springframework.http.ResponseEntity

interface MonitoredEndpointDestroyer {
    fun deleteById(envelope: MonitoredEndpointDeleteEnvelope): ResponseEntity<Unit>
}
