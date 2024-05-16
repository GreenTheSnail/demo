package applifting.task.demo.domain.feature.monitoringresults.extract

import applifting.task.demo.domain.feature.model.spawnMonitoredEndpoint
import applifting.task.demo.domain.feature.model.spawnMonitoringResult
import applifting.task.demo.domain.feature.model.spawnUser
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import org.springframework.http.HttpStatus.FORBIDDEN
import org.springframework.http.HttpStatus.NOT_FOUND
import java.util.UUID.randomUUID

class DefaultMonitoringResultsExtractorTest : StringSpec({
    val provider = mockk<MonitoringResultsExtractionProvider>()
    val userProvider = mockk<UserExtractionProvider>()
    val endpointChecker = mockk<MonitoredEndpointCheckProvider>()
    val extractor = DefaultMonitoringResultsExtractor(provider, userProvider, endpointChecker)
    val endpointId = randomUUID()
    val ownerId = randomUUID()
    val userToken = randomUUID()
    val user = spawnUser(id = ownerId, accessToken = userToken)
    val envelope =
        MonitoringResultExtractionEnvelope(endpointId = endpointId, token = userToken)

    "Should extract valid monitoring result" {
        coEvery { userProvider.getUserByAssesToken(userToken) } returns user
        coEvery { endpointChecker.checkIfCanBeAccessedByUserIfEndpointExists(endpointId, ownerId) } returns true
        coEvery { endpointChecker.checkIfEndpointExists(endpointId) } returns true
        coEvery {
            provider.getLastTenMonitoringResultsByEndpointId(endpointId)
        } returns listOf(spawnMonitoringResult(monitoredEndpoint = spawnMonitoredEndpoint(id = endpointId)))

        val result = extractor.getLastTenMonitoringResultsByEndpointId(envelope)
        with(result) {
            hasBody()
            body?.map { it.monitoredEndpoint.id }?.shouldContain(endpointId)
        }
    }

    "Should return not found - user" {
        coEvery { userProvider.getUserByAssesToken(userToken) } returns null
        coEvery { endpointChecker.checkIfCanBeAccessedByUserIfEndpointExists(endpointId, ownerId) } returns true
        coEvery { endpointChecker.checkIfEndpointExists(endpointId) } returns true
        coEvery {
            provider.getLastTenMonitoringResultsByEndpointId(endpointId)
        } returns listOf(spawnMonitoringResult(monitoredEndpoint = spawnMonitoredEndpoint(id = endpointId)))

        val result = extractor.getLastTenMonitoringResultsByEndpointId(envelope)
        with(result) {
            !hasBody()
            statusCode.value() shouldBe NOT_FOUND.value()
        }
    }

    "Should return forbidden" {
        coEvery { userProvider.getUserByAssesToken(userToken) } returns user
        coEvery { endpointChecker.checkIfCanBeAccessedByUserIfEndpointExists(endpointId, ownerId) } returns false
        coEvery { endpointChecker.checkIfEndpointExists(endpointId) } returns true
        coEvery {
            provider.getLastTenMonitoringResultsByEndpointId(endpointId)
        } returns listOf(spawnMonitoringResult(monitoredEndpoint = spawnMonitoredEndpoint(id = endpointId)))

        val result = extractor.getLastTenMonitoringResultsByEndpointId(envelope)
        with(result) {
            !hasBody()
            statusCode.value() shouldBe FORBIDDEN.value()
        }
    }

    "Should return not found - endpoint" {
        coEvery { userProvider.getUserByAssesToken(userToken) } returns user
        coEvery { endpointChecker.checkIfCanBeAccessedByUserIfEndpointExists(endpointId, ownerId) } returns true
        coEvery { endpointChecker.checkIfEndpointExists(endpointId) } returns false
        coEvery {
            provider.getLastTenMonitoringResultsByEndpointId(endpointId)
        } returns listOf(spawnMonitoringResult(monitoredEndpoint = spawnMonitoredEndpoint(id = endpointId)))

        val result = extractor.getLastTenMonitoringResultsByEndpointId(envelope)
        with(result) {
            !hasBody()
            statusCode.value() shouldBe NOT_FOUND.value()
        }
    }
})
