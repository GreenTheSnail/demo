package applifting.task.demo.integration.feature

import applifting.task.demo.domain.feature.monitoredendpoint.check.MonitoredEndpointChecker
import org.springframework.stereotype.Component
import java.util.UUID
import applifting.task.demo.domain.feature.monitoredendpoint.delete.MonitoredEndpointCheckProvider as MonitoringEndpointDeleteMonitoredEndpointCheckProvider
import applifting.task.demo.domain.feature.monitoredendpoint.extract.MonitoredEndpointCheckProvider as MonitoringEndpointExtractMonitoredEndpointCheckProvider
import applifting.task.demo.domain.feature.monitoredendpoint.update.MonitoredEndpointCheckProvider as MonitoringEndpointUpdateMonitoredEndpointCheckProvider
import applifting.task.demo.domain.feature.monitoringresults.extract.MonitoredEndpointCheckProvider as MonitoringResultsExtractMonitoredEndpointCheckProvider

@Component
class MonitoredEndpointCheckBridge(
    private val checker: MonitoredEndpointChecker,
) : MonitoringResultsExtractMonitoredEndpointCheckProvider,
    MonitoringEndpointExtractMonitoredEndpointCheckProvider,
    MonitoringEndpointUpdateMonitoredEndpointCheckProvider,
    MonitoringEndpointDeleteMonitoredEndpointCheckProvider {
    override fun checkIfCanBeAccessedByUserIfEndpointExists(
        endpointId: UUID,
        userId: UUID,
    ) = checker.checkIfCanBeAccessedByUserIfEndpointExists(
        endpointId = endpointId,
        userId = userId,
    )

    override fun checkIfEndpointExists(endpointId: UUID) = checker.checkIfEndpointExists(endpointId)
}
