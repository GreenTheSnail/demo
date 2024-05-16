package applifting.task.demo.domain.feature.monitoredendpoint.checkdateset

import org.springframework.stereotype.Component
import java.util.UUID

@Component
class DefaultMonitoredEndpointLastCheckDateSetter(
    private val provider: MonitoredEndpointLastCheckDateSetterProvider,
) : MonitoredEndpointLastCheckDateSetter {
    override fun updateDateOfLastCheckById(id: UUID) = provider.updateDateOfLastCheckById(id)
}
