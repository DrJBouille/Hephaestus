package com.hephaestus.api.world.api.dto

import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Length

data class CreateWorldRequestDto(
    @field:NotBlank
    @field:Length(max = 100)
    val name: String
)
