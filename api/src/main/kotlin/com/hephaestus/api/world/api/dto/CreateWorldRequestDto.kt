package com.hephaestus.api.world.api.dto

import com.hephaestus.api.world.domain.Environment
import com.hephaestus.api.world.domain.WorldType
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.Length

data class CreateWorldRequestDto(
    @field:NotBlank
    @field:NotNull
    @field:Length(max = 100)
    var name: String,

    @field:NotNull
    var environment: Environment,

    @field:NotNull
    var worldType: WorldType
)
