package applifting.task.demo.domain.feature.monitoredendpoint.check

import java.util.UUID

interface MonitoredEndpointCheckProvider {
    fun checkIfCanBeAccessedByUserIfEndpointExists(
        endpointId: UUID,
        userId: UUID,
    ): Boolean

    fun checkIfEndpointExists(endpointId: UUID): Boolean
}
