package applifting.task.demo.domain.feature.monitoredendpoint.create

import applifting.task.demo.domain.feature.model.spawnMonitoredEndpoint
import applifting.task.demo.domain.feature.model.spawnMonitoredEndpointCreationEnvelopeExtended
import applifting.task.demo.domain.feature.model.spawnMonitoredEndpointCreationEnvelopeWithToken
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
import java.util.UUID.randomUUID

class DefaultMonitoredEndpointCreatorTest : StringSpec({

    val provider = mockk<MonitoredEndpointCreationProvider>()
    val userProvider = mockk<UserExtractionProvider>()
    val monitor = mockk<EndpointMonitorComponent>()
    val endpointId = randomUUID()
    val ownerId = randomUUID()
    val userToken = randomUUID()
    val owner = spawnUser(id = ownerId, accessToken = userToken)
    val providerEnvelope =
        spawnMonitoredEndpointCreationEnvelopeExtended(owner = owner)
    val envelope = spawnMonitoredEndpointCreationEnvelopeWithToken(userToken = userToken)
    val creator = DefaultMonitoredEndpointCreator(provider, userProvider, monitor)
    val updatedMonitoredEndpoint =
        spawnMonitoredEndpoint(
            id = endpointId,
            name = "endpoint2",
            url = "http://localhost:9091/endpoint2",
            monitoredInterval = 90,
            owner = owner,
        )

    "Should create monitored endpoint" {
        coEvery { userProvider.getUserByAssesToken(userToken) } returns owner
        coEvery { provider.createMonitoredEndpoint(providerEnvelope) } returns updatedMonitoredEndpoint
        coEvery { monitor.addMonitoredEndpoint(updatedMonitoredEndpoint) } just runs

        val result = creator.createMonitoredEndpoint(envelope)
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
        coEvery { userProvider.getUserByAssesToken(userToken) } returns null
        coEvery { provider.createMonitoredEndpoint(providerEnvelope) } returns updatedMonitoredEndpoint
        coEvery { monitor.addMonitoredEndpoint(updatedMonitoredEndpoint) } just runs

        val result = creator.createMonitoredEndpoint(envelope)
        with(result) {
            !hasBody()
            statusCode.value() shouldBe FORBIDDEN.value()
        }
    }
    "Should return bad request" {
        val invalidEnvelope =
            spawnMonitoredEndpointCreationEnvelopeWithToken(url = "INVALID", userToken = userToken)

        coEvery { userProvider.getUserByAssesToken(userToken) } returns owner
        coEvery { provider.createMonitoredEndpoint(providerEnvelope) } returns updatedMonitoredEndpoint
        coEvery { monitor.addMonitoredEndpoint(updatedMonitoredEndpoint) } just runs

        val result = creator.createMonitoredEndpoint(invalidEnvelope)
        with(result) {
            !hasBody()
            statusCode.value() shouldBe BAD_REQUEST.value()
        }
    }
})
