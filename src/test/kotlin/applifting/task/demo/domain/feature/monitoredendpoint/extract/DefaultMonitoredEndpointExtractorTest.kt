package applifting.task.demo.domain.feature.monitoredendpoint.extract

import applifting.task.demo.domain.feature.model.spawnMonitoredEndpoint
import applifting.task.demo.domain.feature.model.spawnUser
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import org.springframework.http.HttpStatus.FORBIDDEN
import org.springframework.http.HttpStatus.NOT_FOUND
import java.util.UUID.randomUUID

class DefaultMonitoredEndpointExtractorTest : StringSpec(
    {
        val provider = mockk<MonitoredEndpointExtractionProvider>()
        val userProvider = mockk<UserExtractionProvider>()
        val endpointChecker = mockk<MonitoredEndpointCheckProvider>()
        val extractor = DefaultMonitoredEndpointExtractor(provider, userProvider, endpointChecker)
        val endpointId = randomUUID()
        val ownerId = randomUUID()
        val userToken = randomUUID()
        val envelope =
            MonitoredEndpointExtractionEnvelope(
                endpointId = endpointId,
                userToken = userToken,
            )

        val user = spawnUser(id = ownerId, accessToken = userToken)
        val monitoredEndpoint = spawnMonitoredEndpoint(id = endpointId)
        "Should extract valid monitored endpoint" {
            coEvery { userProvider.getUserByAssesToken(userToken) } returns user
            coEvery { endpointChecker.checkIfCanBeAccessedByUserIfEndpointExists(endpointId, ownerId) } returns true
            coEvery { provider.getMonitoredEndpointById(endpointId) } returns monitoredEndpoint

            val result = extractor.getMonitoredEndpoint(envelope)
            with(result) {
                hasBody()
                body?.id shouldBe endpointId
            }
        }

        "Should return forbidden" {
            coEvery { userProvider.getUserByAssesToken(userToken) } returns user
            coEvery { endpointChecker.checkIfCanBeAccessedByUserIfEndpointExists(endpointId, ownerId) } returns false
            coEvery { provider.getMonitoredEndpointById(endpointId) } returns monitoredEndpoint

            val result = extractor.getMonitoredEndpoint(envelope)
            with(result) {
                !hasBody()
                statusCode.value() shouldBe FORBIDDEN.value()
            }
        }

        "Should return not found - user" {
            coEvery { userProvider.getUserByAssesToken(userToken) } returns null
            coEvery { endpointChecker.checkIfCanBeAccessedByUserIfEndpointExists(endpointId, ownerId) } returns true
            coEvery { provider.getMonitoredEndpointById(endpointId) } returns monitoredEndpoint

            val result = extractor.getMonitoredEndpoint(envelope)
            with(result) {
                !hasBody()
                statusCode.value() shouldBe NOT_FOUND.value()
            }
        }

        "Should return not found - endpoint" {
            coEvery { userProvider.getUserByAssesToken(userToken) } returns user
            coEvery { endpointChecker.checkIfCanBeAccessedByUserIfEndpointExists(endpointId, ownerId) } returns true
            coEvery { provider.getMonitoredEndpointById(endpointId) } returns null

            val result = extractor.getMonitoredEndpoint(envelope)
            with(result) {
                !hasBody()
                statusCode.value() shouldBe NOT_FOUND.value()
            }
        }
    },
)
