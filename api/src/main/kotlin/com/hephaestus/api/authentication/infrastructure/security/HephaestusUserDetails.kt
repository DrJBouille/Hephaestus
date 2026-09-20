package com.hephaestus.api.authentication.infrastructure.security

import com.hephaestus.api.authentication.domain.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.UUID

class HephaestusUserDetails(private val user: User) : UserDetails {
    val userId: UUID
        get() = user.id.value

    override fun getAuthorities(): Collection<GrantedAuthority> = user.authorities.map { SimpleGrantedAuthority(it.name) }

    override fun getPassword(): String = user.passwordHash

    override fun getUsername(): String = user.username
}