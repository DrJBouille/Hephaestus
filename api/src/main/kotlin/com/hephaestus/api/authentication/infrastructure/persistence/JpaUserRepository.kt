package com.hephaestus.api.authentication.infrastructure.persistence

import com.hephaestus.api.authentication.domain.UserId
import org.springframework.data.repository.CrudRepository

interface JpaUserRepository : CrudRepository<UserEntity, UserId> {
    fun findByUsername(username: String): UserEntity?
    fun existsByUsername(username: String): Boolean
}