package applifting.task.demo.persistence.dao

import applifting.task.demo.domain.feature.monitoredendpoint.check.MonitoredEndpointCheckProvider
import applifting.task.demo.domain.feature.monitoredendpoint.checkdateset.MonitoredEndpointLastCheckDateSetterProvider
import applifting.task.demo.domain.feature.monitoredendpoint.create.MonitoredEndpointCreationEnvelopeExtended
import applifting.task.demo.domain.feature.monitoredendpoint.create.MonitoredEndpointCreationProvider
import applifting.task.demo.domain.feature.monitoredendpoint.delete.MonitoredEndpointDeletingProvider
import applifting.task.demo.domain.feature.monitoredendpoint.extract.MonitoredEndpointExtractionProvider
import applifting.task.demo.domain.feature.monitoredendpoint.update.MonitoredEndpointUpdateEnvelopeExtended
import applifting.task.demo.domain.feature.monitoredendpoint.update.MonitoredEndpointUpdateProvider
import applifting.task.demo.domain.model.MonitoredEndpoint
import applifting.task.demo.persistence.model.toDomain
import applifting.task.demo.persistence.model.toPersistence
import applifting.task.demo.persistence.repository.MonitoredEndpointRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime
import java.time.OffsetDateTime.now
import java.util.UUID
import kotlin.jvm.optionals.getOrNull

@Transactional
@Component
class MonitoredEndpointDAO(
    private val monitoredEndpointRepository: MonitoredEndpointRepository,
) : MonitoredEndpointExtractionProvider,
    MonitoredEndpointUpdateProvider,
    MonitoredEndpointCreationProvider,
    MonitoredEndpointDeletingProvider,
    MonitoredEndpointCheckProvider,
    MonitoredEndpointLastCheckDateSetterProvider {
    @Transactional(readOnly = true)
    override fun getMonitoredEndpointById(id: UUID) = monitoredEndpointRepository.getMonitoredEndpointById(id)?.toDomain()

    override fun getMonitoredEndpointsByOwnerId(ownerId: UUID): List<MonitoredEndpoint> =
        monitoredEndpointRepository.getMonitoredEndpointsByOwnerId(ownerId).map {
            it.toDomain()
        }

    override fun updateMonitoredEndpoint(envelope: MonitoredEndpointUpdateEnvelopeExtended): MonitoredEndpoint? {
        val existingMonitoredEndpoint = monitoredEndpointRepository.getMonitoredEndpointById(envelope.id)?.toDomain()
        return existingMonitoredEndpoint?.mergeWithEnvelope(
            envelope,
        )?.let { monitoredEndpointRepository.save(it.toPersistence()) }?.toDomain()
    }

    override fun updateDateOfLastCheckById(
        id: UUID,
        dateOfLastCheck: OffsetDateTime,
    ) {
        val existingEndpoint = monitoredEndpointRepository.getMonitoredEndpointById(id)
        existingEndpoint?.let { monitoredEndpointRepository.save(it.copy(dateOfLastCheck = dateOfLastCheck)) }
    }

    override fun createMonitoredEndpoint(envelope: MonitoredEndpointCreationEnvelopeExtended) =
        monitoredEndpointRepository.save(envelope.toDomainObject().toPersistence()).toDomain()

    override fun deleteById(id: UUID) =
        monitoredEndpointRepository.findById(id).getOrNull()?.let { monitoredEndpointRepository.deleteById(id) }

    override fun getAllMonitoredEndpoints() = monitoredEndpointRepository.findAll().map { it.toDomain() }

    override fun checkIfCanBeAccessedByUserIfEndpointExists(
        endpointId: UUID,
        userId: UUID,
    ) = monitoredEndpointRepository.getMonitoredEndpointById(endpointId).let { it == null || it.owner.id == userId }

    override fun checkIfEndpointExists(endpointId: UUID) = monitoredEndpointRepository.getMonitoredEndpointById(endpointId) != null
}

private fun MonitoredEndpoint.mergeWithEnvelope(envelope: MonitoredEndpointUpdateEnvelopeExtended) =
    copy(
        name = envelope.name ?: name,
        url = envelope.url ?: url,
        dateOfLastUpdate = now(),
        monitoredInterval = envelope.monitoredInterval ?: monitoredInterval,
        owner = envelope.owner ?: owner,
    )

private fun MonitoredEndpointCreationEnvelopeExtended.toDomainObject() =
    MonitoredEndpoint(
        name = name,
        url = url,
        dateOfLastUpdate = now(),
        monitoredInterval = monitoredInterval,
        owner = owner,
    )
