package com.hephaestus.api.world.api.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.hephaestus.api.world.domain.Environment
import com.hephaestus.api.world.domain.WorldStatus
import com.hephaestus.api.world.domain.WorldType
import java.time.Instant
import java.util.UUID

data class WorldResponse(
    val id: UUID,
    val name: String,
    val environment: Environment,
    val worldType: WorldType,
    val status: WorldStatus,
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    val createdAt: Instant,
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    val updatedAt: Instant,
)
