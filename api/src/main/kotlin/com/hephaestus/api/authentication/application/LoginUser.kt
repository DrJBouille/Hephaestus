package com.hephaestus.api.authentication.application

import com.hephaestus.api.authentication.application.data.LoginResponse
import com.hephaestus.api.authentication.infrastructure.security.JwtService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service

@Service
class LoginUser(
    private val authenticationManager: AuthenticationManager,
    private val jwtService: JwtService

) {
    fun execute(username: String, password: String) : LoginResponse {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(username, password)
        )

        return LoginResponse(jwtService.generate(authentication))
    }
}