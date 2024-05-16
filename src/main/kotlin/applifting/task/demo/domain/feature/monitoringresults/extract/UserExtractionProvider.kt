package applifting.task.demo.domain.feature.monitoringresults.extract

import applifting.task.demo.domain.model.User
import java.util.UUID

interface UserExtractionProvider {
    fun getUserByAssesToken(token: UUID): User?
}
