package com.hephaestus.api.authentication.api.dto

import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Length

data class RegisterRequestDto(
    @field:NotBlank
    @field:Length(min = 3, max = 16)
    val username: String,

    @field:NotBlank
    @field:Length(min = 6, max = 64)
    val password: String
)
