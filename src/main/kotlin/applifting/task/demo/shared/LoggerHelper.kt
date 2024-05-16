package applifting.task.demo.shared

import org.slf4j.Logger
import java.util.UUID

fun Logger.logUserNotFound(token: UUID) {
    error("User with token $token not found")
}

fun Logger.logEndpointNotFound(id: UUID) {
    error("Endpoint with id $id not found")
}

fun Logger.logForbidden(endpointId: UUID) {
    error("User dos not have access to endpoint with id $endpointId")
}
