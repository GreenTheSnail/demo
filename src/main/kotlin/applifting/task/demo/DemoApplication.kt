package applifting.task.demo

import applifting.task.demo.domain.feature.monitoredendpoint.EndpointMonitorComponent
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.runApplication
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@SpringBootApplication
class DemoApplication

@Component
class OnStartupRunner(
    private val monitor: EndpointMonitorComponent,
) {
    @EventListener(ApplicationReadyEvent::class)
    fun onApplicationReady() {
        monitor.initializeMonitorEndpoints()
    }
}

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}
