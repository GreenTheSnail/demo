package applifting.task.demo.persistence.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.util.UUID
import applifting.task.demo.domain.model.User as UserDomainObject

@Entity(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID,
    val username: String,
    val email: String,
    val accessToken: UUID,
)

fun User.toDomain() =
    UserDomainObject(
        id = id,
        username = username,
        email = email,
        accessToken = accessToken,
    )

fun UserDomainObject.toPersistence() =
    User(
        id = id,
        username = username,
        email = email,
        accessToken = accessToken,
    )
