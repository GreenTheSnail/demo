package applifting.task.demo.domain.feature.monitoredendpoint.update

import applifting.task.demo.domain.model.User
import java.util.UUID

interface UserExtractionProvider {
    fun getUserById(id: UUID): User?

    fun getUserByAssesToken(token: UUID): User?
}
