package applifting.task.demo.domain.feature.model

import applifting.task.demo.domain.feature.monitoredendpoint.create.MonitoredEndpointCreationEnvelopeExtended
import applifting.task.demo.domain.feature.monitoredendpoint.create.MonitoredEndpointCreationEnvelopeWithToken
import applifting.task.demo.domain.feature.monitoredendpoint.update.MonitoredEndpointUpdateEnvelopeExtended
import applifting.task.demo.domain.feature.monitoredendpoint.update.MonitoredEndpointUpdateEnvelopeWithToken
import applifting.task.demo.domain.model.MonitoredEndpoint
import applifting.task.demo.domain.model.MonitoringResult
import applifting.task.demo.domain.model.User
import java.time.OffsetDateTime
import java.time.OffsetDateTime.now
import java.util.UUID
import java.util.UUID.randomUUID

fun spawnUser(
    id: UUID = randomUUID(),
    username: String = "user1",
    email: String = "user1@test.com",
    accessToken: UUID = randomUUID(),
) = User(
    id = id,
    username = username,
    email = email,
    accessToken = accessToken,
)

fun spawnMonitoredEndpoint(
    id: UUID = randomUUID(),
    name: String = "endpoint1",
    url: String = "http://localhost:9091/endpoint1",
    dateOfLastUpdate: OffsetDateTime = now(),
    dateOfLastCheck: OffsetDateTime = now(),
    monitoredInterval: Int = 60,
    owner: User = spawnUser(),
) = MonitoredEndpoint(
    id = id,
    name = name,
    url = url,
    dateOfLastUpdate = dateOfLastUpdate,
    dateOfLastCheck = dateOfLastCheck,
    monitoredInterval = monitoredInterval,
    owner = owner,
)

fun spawnMonitoredEndpointUpdateEnvelopeExtended(
    id: UUID = randomUUID(),
    name: String = "endpoint2",
    url: String = "http://localhost:9091/endpoint2",
    monitoredInterval: Int = 90,
    owner: User = spawnUser(),
) = MonitoredEndpointUpdateEnvelopeExtended(
    id = id,
    name = name,
    url = url,
    monitoredInterval = monitoredInterval,
    owner = owner,
)

fun spawnMonitoredEndpointCreationEnvelopeExtended(
    name: String = "endpoint2",
    url: String = "http://localhost:9091/endpoint2",
    monitoredInterval: Int = 90,
    owner: User = spawnUser(),
) = MonitoredEndpointCreationEnvelopeExtended(
    name = name,
    url = url,
    monitoredInterval = monitoredInterval,
    owner = owner,
)

fun spawnMonitoredEndpointUpdateEnvelopeWithToken(
    id: UUID = randomUUID(),
    name: String = "endpoint2",
    url: String = "http://localhost:9091/endpoint2",
    monitoredInterval: Int = 90,
    userToken: UUID = randomUUID(),
    ownerId: UUID = randomUUID(),
) = MonitoredEndpointUpdateEnvelopeWithToken(
    id = id,
    name = name,
    url = url,
    monitoredInterval = monitoredInterval,
    userToken = userToken,
    ownerId = ownerId,
)

fun spawnMonitoredEndpointCreationEnvelopeWithToken(
    name: String = "endpoint2",
    url: String = "http://localhost:9091/endpoint2",
    monitoredInterval: Int = 90,
    userToken: UUID = randomUUID(),
) = MonitoredEndpointCreationEnvelopeWithToken(
    name = name,
    url = url,
    monitoredInterval = monitoredInterval,
    token = userToken,
)

fun spawnMonitoringResult(
    id: UUID = randomUUID(),
    dateOfCheck: OffsetDateTime = now(),
    httpStatusCode: Int = 200,
    payload: String = "test",
    monitoredEndpoint: MonitoredEndpoint = spawnMonitoredEndpoint(),
) = MonitoringResult(
    id = id,
    dateOfCheck = dateOfCheck,
    httpStatusCode = httpStatusCode,
    payload = payload,
    monitoredEndpoint = monitoredEndpoint,
)
