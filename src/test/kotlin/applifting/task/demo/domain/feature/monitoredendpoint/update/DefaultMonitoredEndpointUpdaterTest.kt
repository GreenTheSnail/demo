package applifting.task.demo.domain.feature.monitoredendpoint.update

import applifting.task.demo.domain.feature.model.spawnMonitoredEndpoint
import applifting.task.demo.domain.feature.model.spawnMonitoredEndpointUpdateEnvelopeExtended
import applifting.task.demo.domain.feature.model.spawnMonitoredEndpointUpdateEnvelopeWithToken
import applifting.task.demo.domain.feature.model.spawnUser
import applifting.task.demo.domain.feature.monitoredendpoint.EndpointMonitorComponent
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.FORBIDDEN
import org.springframework.http.HttpStatus.NOT_FOUND
import java.util.UUID.randomUUID

class DefaultMonitoredEndpointUpdaterTest : StringSpec({

    val provider = mockk<MonitoredEndpointUpdateProvider>()
    val userProvider = mockk<UserExtractionProvider>()
    val monitor = mockk<EndpointMonitorComponent>()
    val endpointChecker = mockk<MonitoredEndpointCheckProvider>()
    val updater = DefaultMonitoredEndpointUpdater(provider, userProvider, monitor, endpointChecker)
    val endpointId = randomUUID()
    val ownerId = randomUUID()
    val userToken = randomUUID()
    val owner = spawnUser(id = ownerId, accessToken = userToken)
    val providerEnvelope =
        spawnMonitoredEndpointUpdateEnvelopeExtended(id = endpointId, owner = owner)
    val envelope = spawnMonitoredEndpointUpdateEnvelopeWithToken(id = endpointId, userToken = userToken, ownerId = ownerId)
    val updatedMonitoredEndpoint =
        spawnMonitoredEndpoint(
            id = endpointId,
            name = "endpoint2",
            url = "http://localhost:9091/endpoint2",
            monitoredInterval = 90,
            owner = owner,
        )

    "Should update monitored endpoint" {
        coEvery { userProvider.getUserByAssesToken(userToken) } returns owner
        coEvery { userProvider.getUserById(ownerId) } returns owner
        coEvery { endpointChecker.checkIfCanBeAccessedByUserIfEndpointExists(endpointId, ownerId) } returns true
        coEvery { provider.updateMonitoredEndpoint(providerEnvelope) } returns updatedMonitoredEndpoint
        coEvery { monitor.updateMonitoredEndpoint(updatedMonitoredEndpoint) } just runs

        val result = updater.updateMonitoredEndpoint(envelope)
        with(result) {
            hasBody()
            body?.id shouldBe endpointId
            body?.name shouldBe providerEnvelope.name
            body?.url shouldBe providerEnvelope.url
            body?.monitoredInterval shouldBe providerEnvelope.monitoredInterval
            body?.owner shouldBe providerEnvelope.owner
        }
    }
    "Should return forbidden" {
        coEvery { userProvider.getUserByAssesToken(userToken) } returns owner
        coEvery { userProvider.getUserById(ownerId) } returns owner
        coEvery { endpointChecker.checkIfCanBeAccessedByUserIfEndpointExists(endpointId, ownerId) } returns false
        coEvery { provider.updateMonitoredEndpoint(providerEnvelope) } returns updatedMonitoredEndpoint
        coEvery { monitor.updateMonitoredEndpoint(updatedMonitoredEndpoint) } just runs

        val result = updater.updateMonitoredEndpoint(envelope)
        with(result) {
            !hasBody()
            statusCode.value() shouldBe FORBIDDEN.value()
        }
    }
    "Should return not found - user" {
        coEvery { userProvider.getUserByAssesToken(userToken) } returns null
        coEvery { userProvider.getUserById(ownerId) } returns owner
        coEvery { endpointChecker.checkIfCanBeAccessedByUserIfEndpointExists(endpointId, ownerId) } returns false
        coEvery { provider.updateMonitoredEndpoint(providerEnvelope) } returns updatedMonitoredEndpoint
        coEvery { monitor.updateMonitoredEndpoint(updatedMonitoredEndpoint) } just runs

        val result = updater.updateMonitoredEndpoint(envelope)
        with(result) {
            !hasBody()
            statusCode.value() shouldBe NOT_FOUND.value()
        }
    }

    "Should return bad request - new user" {
        coEvery { userProvider.getUserByAssesToken(userToken) } returns owner
        coEvery { userProvider.getUserById(ownerId) } returns null
        coEvery { endpointChecker.checkIfCanBeAccessedByUserIfEndpointExists(endpointId, ownerId) } returns true
        coEvery { provider.updateMonitoredEndpoint(providerEnvelope) } returns updatedMonitoredEndpoint
        coEvery { monitor.updateMonitoredEndpoint(updatedMonitoredEndpoint) } just runs

        val result = updater.updateMonitoredEndpoint(envelope)
        with(result) {
            !hasBody()
            statusCode.value() shouldBe BAD_REQUEST.value()
        }
    }

    "Should return not found - endpoint" {
        coEvery { userProvider.getUserByAssesToken(userToken) } returns owner
        coEvery { userProvider.getUserById(ownerId) } returns owner
        coEvery { endpointChecker.checkIfCanBeAccessedByUserIfEndpointExists(endpointId, ownerId) } returns true
        coEvery { provider.updateMonitoredEndpoint(providerEnvelope) } returns null
        coEvery { monitor.updateMonitoredEndpoint(updatedMonitoredEndpoint) } just runs

        val result = updater.updateMonitoredEndpoint(envelope)
        with(result) {
            !hasBody()
            statusCode.value() shouldBe NOT_FOUND.value()
        }
    }

    "Should return bad request" {
        val invalidEnvelope =
            spawnMonitoredEndpointUpdateEnvelopeWithToken(url = "INVALID", id = endpointId, userToken = userToken, ownerId = ownerId)

        coEvery { userProvider.getUserByAssesToken(userToken) } returns owner
        coEvery { userProvider.getUserById(ownerId) } returns owner
        coEvery { endpointChecker.checkIfCanBeAccessedByUserIfEndpointExists(endpointId, ownerId) } returns true
        coEvery { provider.updateMonitoredEndpoint(providerEnvelope) } returns null
        coEvery { monitor.updateMonitoredEndpoint(updatedMonitoredEndpoint) } just runs

        val result = updater.updateMonitoredEndpoint(invalidEnvelope)
        with(result) {
            !hasBody()
            statusCode.value() shouldBe BAD_REQUEST.value()
        }
    }
})
