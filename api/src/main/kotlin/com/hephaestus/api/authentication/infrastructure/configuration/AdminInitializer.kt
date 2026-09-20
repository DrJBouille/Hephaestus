package com.hephaestus.api.authentication.infrastructure.configuration

import com.hephaestus.api.authentication.domain.User
import com.hephaestus.api.authentication.domain.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class AdminInitializer(
    private val properties: HephaestusAdminProperties,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
) : CommandLineRunner {

    override fun run(vararg args: String) {
        if (properties.username.isBlank() || properties.password.isBlank()) return


        if (userRepository.existsByUsername(properties.username)) return

        val passwordHash = passwordEncoder.encode(properties.password)

        val admin = User.createAdmin(
            properties.username,
            passwordHash!!,
        )

        userRepository.save(admin)
    }
}