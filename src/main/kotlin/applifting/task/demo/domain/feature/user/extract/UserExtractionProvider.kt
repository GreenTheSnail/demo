package applifting.task.demo.domain.feature.user.extract

import applifting.task.demo.domain.model.User
import java.util.UUID

interface UserExtractionProvider {
    fun getUserByAccessToken(token: UUID): User?

    fun getUserById(id: UUID): User?
}
