package applifting.task.demo.persistence.repository

import applifting.task.demo.persistence.model.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserRepository : CrudRepository<User, UUID> {
    fun getUserByAccessToken(token: UUID): User?

    fun getUserById(token: UUID): User?
}
