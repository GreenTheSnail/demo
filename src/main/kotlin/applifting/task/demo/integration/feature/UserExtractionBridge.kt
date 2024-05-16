package applifting.task.demo.integration.feature

import applifting.task.demo.domain.feature.user.extract.UserExtractor
import org.springframework.stereotype.Component
import java.util.UUID
import applifting.task.demo.domain.feature.monitoredendpoint.create.UserExtractionProvider as MonitoredEndpointCreateUserExtractionProvider
import applifting.task.demo.domain.feature.monitoredendpoint.delete.UserExtractionProvider as MonitoredEndpointDeleteUserExtractionProvider
import applifting.task.demo.domain.feature.monitoredendpoint.extract.UserExtractionProvider as MonitoredEndpointExtractUserExtractionProvider
import applifting.task.demo.domain.feature.monitoredendpoint.update.UserExtractionProvider as MonitoredEndpointUpdateUserExtractionProvider
import applifting.task.demo.domain.feature.monitoringresults.extract.UserExtractionProvider as MonitoringResultsExtractUserExtractionProvider

@Component
class UserExtractionBridge(
    private val extractor: UserExtractor,
) : MonitoredEndpointUpdateUserExtractionProvider,
    MonitoredEndpointCreateUserExtractionProvider,
    MonitoredEndpointExtractUserExtractionProvider,
    MonitoringResultsExtractUserExtractionProvider,
    MonitoredEndpointDeleteUserExtractionProvider {
    override fun getUserByAssesToken(token: UUID) = extractor.getUserByAccessToken(token)

    override fun getUserById(id: UUID) = extractor.getUserById(id)
}
