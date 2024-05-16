package applifting.task.demo.domain.feature.monitoringresults.create

import applifting.task.demo.domain.model.MonitoringResult
import org.springframework.stereotype.Component

@Component
class DefaultMonitoringResultCreator(
    private val provider: MonitoringResultCreationProvider,
) : MonitoringResultCreator {
    override fun saveMonitoringResult(envelope: MonitoringResult) = provider.saveMonitoringResult(envelope)
}
