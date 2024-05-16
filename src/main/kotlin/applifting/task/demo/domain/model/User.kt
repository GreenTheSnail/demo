package applifting.task.demo.domain.model

import java.util.UUID

data class User(
    val id: UUID,
    val username: String,
    val email: String,
    val accessToken: UUID,
)
