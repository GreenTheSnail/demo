package applifting.task.demo.domain.feature.monitoredendpoint.update

import java.util.UUID

interface MonitoredEndpointCheckProvider {
    fun checkIfCanBeAccessedByUserIfEndpointExists(
        endpointId: UUID,
        userId: UUID,
    ): Boolean
}
