package applifting.task.demo.persistence.dao

import applifting.task.demo.domain.feature.user.extract.UserExtractionProvider
import applifting.task.demo.persistence.model.toDomain
import applifting.task.demo.persistence.repository.UserRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Transactional
@Component
class UserDAO(
    private val userRepository: UserRepository,
) : UserExtractionProvider {
    @Transactional(readOnly = true)
    override fun getUserByAccessToken(token: UUID) = userRepository.getUserByAccessToken(token)?.toDomain()

    override fun getUserById(id: UUID) = userRepository.getUserById(id)?.toDomain()
}
