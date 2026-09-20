package com.hephaestus.api.authentication.application

import com.hephaestus.api.authentication.application.data.LoginResponse
import com.hephaestus.api.authentication.application.exceptions.PasswordEncodeException
import com.hephaestus.api.authentication.domain.User
import com.hephaestus.api.authentication.domain.UserRepository
import com.hephaestus.api.authentication.infrastructure.security.JwtService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class RegisterUser(
    private val repository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationManager: AuthenticationManager,
    private val jwtService: JwtService
) {
    fun execute(username: String, password: String): LoginResponse {
        require(!repository.existsByUsername(username)) { "User $username already exists" }

        val passwordHash = passwordEncoder.encode(password) ?: throw PasswordEncodeException()

        val user = User.create(username, passwordHash)

        repository.save(user)

        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(username, password)
        )

        return LoginResponse(jwtService.generate(authentication))
    }
}