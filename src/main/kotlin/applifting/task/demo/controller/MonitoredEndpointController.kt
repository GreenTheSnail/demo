package applifting.task.demo.controller

import applifting.task.demo.controller.mapping.addToken
import applifting.task.demo.controller.mapping.convertToUUID
import applifting.task.demo.domain.feature.monitoredendpoint.create.MonitoredEndpointCreationEnvelope
import applifting.task.demo.domain.feature.monitoredendpoint.create.MonitoredEndpointCreator
import applifting.task.demo.domain.feature.monitoredendpoint.delete.MonitoredEndpointDeleteEnvelope
import applifting.task.demo.domain.feature.monitoredendpoint.delete.MonitoredEndpointDestroyer
import applifting.task.demo.domain.feature.monitoredendpoint.extract.MonitoredEndpointExtractionEnvelope
import applifting.task.demo.domain.feature.monitoredendpoint.extract.MonitoredEndpointExtractor
import applifting.task.demo.domain.feature.monitoredendpoint.update.MonitoredEndpointUpdateEnvelope
import applifting.task.demo.domain.feature.monitoredendpoint.update.MonitoredEndpointUpdater
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/monitored-endpoints")
class MonitoredEndpointController(
    private val extractor: MonitoredEndpointExtractor,
    private val creator: MonitoredEndpointCreator,
    private val updater: MonitoredEndpointUpdater,
    private val destroyer: MonitoredEndpointDestroyer,
) {
    @PostMapping
    fun createMonitoredEndpoint(
        @RequestHeader("Authorization") token: String,
        @RequestBody request: MonitoredEndpointCreationEnvelope,
    ) = creator.createMonitoredEndpoint(request.addToken(token))

    @GetMapping
    fun getMonitoredEndpoints(
        @RequestHeader("Authorization") token: String,
    ) = extractor.getMonitoredEndpoints(token.convertToUUID())

    @GetMapping("/{endpointId}")
    fun getMonitoredEndpoint(
        @RequestHeader("Authorization") token: String,
        @PathVariable endpointId: UUID,
    ) = extractor.getMonitoredEndpoint(
        MonitoredEndpointExtractionEnvelope(
            endpointId = endpointId,
            userToken = token.convertToUUID(),
        ),
    )

    @PutMapping
    fun updateMonitoredEndpoint(
        @RequestHeader("Authorization") token: String,
        @RequestBody request: MonitoredEndpointUpdateEnvelope,
    ) = updater.updateMonitoredEndpoint(request.addToken(token))

    @DeleteMapping("/{endpointId}")
    fun deleteMonitoredEndpoint(
        @RequestHeader("Authorization") token: String,
        @PathVariable endpointId: UUID,
    ) = destroyer.deleteById(
        MonitoredEndpointDeleteEnvelope(
            endpointId = endpointId,
            userToken = token.convertToUUID(),
        ),
    )
}
