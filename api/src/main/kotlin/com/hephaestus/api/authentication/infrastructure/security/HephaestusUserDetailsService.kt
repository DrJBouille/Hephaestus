package com.hephaestus.api.authentication.infrastructure.security

import com.hephaestus.api.authentication.domain.UserRepository
import com.hephaestus.api.authentication.infrastructure.exceptions.UserNotFoundException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class HephaestusUserDetailsService(private val repository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = repository.findByUsername(username) ?: throw UserNotFoundException()
        return HephaestusUserDetails(user)
    }
}