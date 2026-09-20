package com.hephaestus.api.authentication.infrastructure.persistence

import com.hephaestus.api.authentication.domain.User
import com.hephaestus.api.authentication.domain.UserId
import com.hephaestus.api.authentication.domain.UserRepository
import org.springframework.stereotype.Repository

@Repository
class UserPersistenceAdapter(private val repository: JpaUserRepository) : UserRepository {
    override fun findByUsername(username: String): User? =
        repository.findByUsername(username)?.toDomain()

    override fun findById(id: UserId): User? =
        repository.findById(id).orElse(null).toDomain()

    override fun existsByUsername(username: String): Boolean =
        repository.existsByUsername(username)

    override fun save(user: User): User =
        repository.save(user.toEntity()).toDomain()

    private fun UserEntity.toDomain(): User = User.reconstitute(
        id = UserId(id),
        username = username,
        passwordHash = passwordHash,
        authorities = authorities.toSet(),
        enabled = enabled,
    )

    private fun User.toEntity(): UserEntity = UserEntity(
        id = id.value,
        username = username,
        passwordHash = passwordHash,
        authorities = authorities.toMutableSet(),
        enabled = enabled
    )
}