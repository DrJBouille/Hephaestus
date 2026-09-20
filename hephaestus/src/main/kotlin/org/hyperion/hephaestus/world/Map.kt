package org.hyperion.hephaestus.world

import org.bukkit.World
import org.bukkit.entity.Player
import java.util.UUID

interface Map {
    val world: World
    val isLocked: Boolean
    val whitelisted: Collection<UUID>

    fun canJoin(player: Player): Boolean
    fun whitelist(player: Player): Boolean
    fun blacklist(player: Player)
    fun unload(): Boolean
}