package applifting.task.demo.domain.feature.monitoringresults.create

import applifting.task.demo.domain.model.MonitoringResult

interface MonitoringResultCreationProvider {
    fun saveMonitoringResult(envelope: MonitoringResult)
}
