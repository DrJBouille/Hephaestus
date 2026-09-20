package org.hyperion.hephaestus.entities

import java.util.UUID

data class WorldDto(
    val id: UUID,
    val name: String,
    val status: WorldStatus,
    val createdAt: String,
    val updatedAt: String,
)

