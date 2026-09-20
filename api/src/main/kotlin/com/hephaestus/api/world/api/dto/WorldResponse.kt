package com.hephaestus.api.world.api.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.hephaestus.api.world.domain.WorldStatus
import java.time.Instant
import java.util.UUID

data class WorldResponse(
    val id: UUID,
    val name: String,
    val status: WorldStatus,
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    val createdAt: Instant,
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    val updatedAt: Instant,
)
