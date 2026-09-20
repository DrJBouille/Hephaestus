package com.hephaestus.api.world.domain

import java.util.UUID

@JvmInline
value class WorldId(val value: UUID) {
    companion object {
        fun generate(): WorldId = WorldId(UUID.randomUUID())
    }
}