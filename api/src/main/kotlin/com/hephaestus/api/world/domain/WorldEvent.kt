package com.hephaestus.api.world.domain

sealed interface WorldEvent {
    val worldId: WorldId
}

data class WorldCreated(override val worldId: WorldId) : WorldEvent
data class WorldUploaded(override val worldId: WorldId) : WorldEvent
data class WorldArchived(override val worldId: WorldId) : WorldEvent