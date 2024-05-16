package applifting.task.demo.domain.feature.monitoredendpoint.update

import applifting.task.demo.domain.model.MonitoredEndpoint

interface MonitoredEndpointUpdateProvider {
    fun updateMonitoredEndpoint(envelope: MonitoredEndpointUpdateEnvelopeExtended): MonitoredEndpoint?
}
