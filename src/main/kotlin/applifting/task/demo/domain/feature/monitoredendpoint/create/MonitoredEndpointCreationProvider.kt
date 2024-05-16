package applifting.task.demo.domain.feature.monitoredendpoint.create

import applifting.task.demo.domain.model.MonitoredEndpoint

interface MonitoredEndpointCreationProvider {
    fun createMonitoredEndpoint(envelope: MonitoredEndpointCreationEnvelopeExtended): MonitoredEndpoint
}
