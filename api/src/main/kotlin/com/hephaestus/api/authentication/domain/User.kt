package com.hephaestus.api.authentication.domain

class User(
    val id: UserId,
    val username: String,
    val passwordHash: String,
    val authorities: Set<Authority>,
    val enabled: Boolean
) {
    companion object {
        fun create(username: String, passwordHash: String): User {
            require(username.isNotBlank()) { "Username cannot be blank" }

            return User(
                id = UserId.generate(),
                username = username,
                passwordHash = passwordHash,
                authorities = setOf(Authority.WORLD_READ, Authority.WORLD_DOWNLOAD),
                enabled = true
            )
        }

        fun createAdmin(username: String, passwordHash: String): User {
            require(username.isNotBlank()) { "Username cannot be blank" }

            return User(
                id = UserId.generate(),
                username = username,
                passwordHash = passwordHash,
                authorities = Authority.entries.toSet(),
                enabled = true
            )
        }

        fun reconstitute(
            id: UserId,
            username: String,
            passwordHash: String,
            authorities: Set<Authority>,
            enabled: Boolean
        ): User = User(
            id = id,
            username = username,
            passwordHash = passwordHash,
            authorities = authorities,
            enabled = enabled
        )
    }
}