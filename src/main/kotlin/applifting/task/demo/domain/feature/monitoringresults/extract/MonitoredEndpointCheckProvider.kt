package applifting.task.demo.domain.feature.monitoringresults.extract

import java.util.UUID

interface MonitoredEndpointCheckProvider {
    fun checkIfCanBeAccessedByUserIfEndpointExists(
        endpointId: UUID,
        userId: UUID,
    ): Boolean

    fun checkIfEndpointExists(endpointId: UUID): Boolean
}
