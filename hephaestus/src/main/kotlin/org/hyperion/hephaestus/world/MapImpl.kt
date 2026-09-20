package org.hyperion.hephaestus.world

import com.jeff_media.morepersistentdatatypes.DataType
import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import org.bukkit.World
import org.bukkit.entity.Player
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.Plugin
import java.util.UUID

class MapImpl(
    override val world: World,
    plugin: Plugin,
    private val maps: MapService
) : Map {
    val isLockKey = NamespacedKey(plugin, "buildteam.lock")
    val whitelistKey = NamespacedKey(plugin, "buildteam.whitelist")

    val pdc = world.persistentDataContainer

    override var isLocked: Boolean
        get() = pdc.get(isLockKey, PersistentDataType.BOOLEAN) ?: false
        set(value) {
            pdc.set(isLockKey, PersistentDataType.BOOLEAN, value)
        }

    override val whitelisted: MutableSet<UUID>
        get() = pdc.get(whitelistKey, DataType.asGenericCollection({ mutableSetOf() }, DataType.UUID)) ?: mutableSetOf()

    override fun canJoin(player: Player): Boolean {
        return !isLocked || player.uniqueId in whitelisted || player.isOp
    }

    override fun whitelist(player: Player): Boolean {
        val whitelist = whitelisted
        if (!whitelist.add(player.uniqueId)) return false

        pdc.set(whitelistKey, DataType.asGenericCollection({ mutableSetOf() }, DataType.UUID), whitelist)

        return true
    }

    override fun blacklist(player: Player) {
        val whitelist = whitelisted
        whitelist.remove(player.uniqueId)
        pdc.set(whitelistKey, DataType.asGenericCollection({ mutableSetOf() }, DataType.UUID), whitelist)
    }

    override fun unload(): Boolean {
        val target = maps.maps.firstOrNull {it != this && !it.isLocked} ?: return false
        for (player in world.players) {
            player.teleport(target.world.spawnLocation)
        }
        return Bukkit.unloadWorld(world, true)
    }
}