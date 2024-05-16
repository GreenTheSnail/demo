package applifting.task.demo.domain.feature.monitoredendpoint.check

import org.springframework.stereotype.Component
import java.util.UUID

@Component
class DefaultMonitoredEndpointChecker(
    private val provider: MonitoredEndpointCheckProvider,
) : MonitoredEndpointChecker {
    override fun checkIfCanBeAccessedByUserIfEndpointExists(
        endpointId: UUID,
        userId: UUID,
    ) = provider.checkIfCanBeAccessedByUserIfEndpointExists(
        endpointId = endpointId,
        userId = userId,
    )

    override fun checkIfEndpointExists(endpointId: UUID) = provider.checkIfEndpointExists(endpointId)
}
