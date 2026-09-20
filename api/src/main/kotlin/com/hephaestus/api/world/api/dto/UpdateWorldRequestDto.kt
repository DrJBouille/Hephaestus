package com.hephaestus.api.world.api.dto

import com.hephaestus.api.world.domain.WorldId
import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Length


data class UpdateWorldRequestDto(
    @field:NotBlank
    val id: WorldId,

    @field:NotBlank
    @field:Length(max = 100)
    val name: String
)
