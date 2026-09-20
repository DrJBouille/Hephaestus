package com.hephaestus.api.authentication.domain

interface UserRepository {
    fun findByUsername(username: String): User?
    fun findById(id: UserId): User?
    fun existsByUsername(username: String): Boolean
    fun save(user: User): User
}