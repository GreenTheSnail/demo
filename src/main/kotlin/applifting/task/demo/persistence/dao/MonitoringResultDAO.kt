package applifting.task.demo.persistence.dao

import applifting.task.demo.domain.feature.monitoringresults.create.MonitoringResultCreationProvider
import applifting.task.demo.domain.feature.monitoringresults.extract.MonitoringResultsExtractionProvider
import applifting.task.demo.domain.model.MonitoringResult
import applifting.task.demo.persistence.model.toDomain
import applifting.task.demo.persistence.model.toPersistence
import applifting.task.demo.persistence.repository.MonitoringResultRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Transactional
@Component
class MonitoringResultDAO(
    private val monitoringResultsRepository: MonitoringResultRepository,
) : MonitoringResultsExtractionProvider,
    MonitoringResultCreationProvider {
    @Transactional(readOnly = true)
    override fun getLastTenMonitoringResultsByEndpointId(id: UUID) =
        monitoringResultsRepository.findTop10ByMonitoredEndpointIdOrderByDateOfCheckDesc(id).map { it.toDomain() }

    override fun saveMonitoringResult(envelope: MonitoringResult) {
        monitoringResultsRepository.save(envelope.toPersistence())
    }
}
