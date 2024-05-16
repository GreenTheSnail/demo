package applifting.task.demo.controller.mapping

import applifting.task.demo.domain.feature.monitoredendpoint.create.MonitoredEndpointCreationEnvelope
import applifting.task.demo.domain.feature.monitoredendpoint.create.MonitoredEndpointCreationEnvelopeWithToken
import applifting.task.demo.domain.feature.monitoredendpoint.update.MonitoredEndpointUpdateEnvelope
import applifting.task.demo.domain.feature.monitoredendpoint.update.MonitoredEndpointUpdateEnvelopeWithToken

fun MonitoredEndpointCreationEnvelope.addToken(token: String) =
    MonitoredEndpointCreationEnvelopeWithToken(
        name = name,
        url = url,
        monitoredInterval = monitoredInterval,
        token = token.convertToUUID(),
    )

fun MonitoredEndpointUpdateEnvelope.addToken(token: String) =
    MonitoredEndpointUpdateEnvelopeWithToken(
        id = id,
        userToken = token.convertToUUID(),
        name = name,
        url = url,
        monitoredInterval = monitoredInterval,
        ownerId = ownerId,
    )
