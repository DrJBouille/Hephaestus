package com.hephaestus.api.authentication.api

import com.hephaestus.api.authentication.api.dto.LoginRequestDto
import com.hephaestus.api.authentication.api.dto.RegisterRequestDto
import com.hephaestus.api.authentication.application.LoginUser
import com.hephaestus.api.authentication.application.RegisterUser
import com.hephaestus.api.authentication.application.data.LoginResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthenticationController(
    private val loginUser: LoginUser,
    private val registerUser: RegisterUser,
) {
    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequestDto): LoginResponse {
        return loginUser.execute(request.username, request.password)
    }

    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequestDto): LoginResponse {
        return registerUser.execute(request.username, request.password)
    }
}