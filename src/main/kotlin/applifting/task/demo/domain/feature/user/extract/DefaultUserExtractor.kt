package applifting.task.demo.domain.feature.user.extract

import org.springframework.stereotype.Component
import java.util.UUID

@Component
class DefaultUserExtractor(private val provider: UserExtractionProvider) : UserExtractor {
    override fun getUserByAccessToken(token: UUID) = provider.getUserByAccessToken(token)

    override fun getUserById(id: UUID) = provider.getUserById(id)
}
