package applifting.task.demo.domain.feature.monitoringresults.create

import applifting.task.demo.domain.model.MonitoringResult

interface MonitoringResultCreator {
    fun saveMonitoringResult(envelope: MonitoringResult)
}
