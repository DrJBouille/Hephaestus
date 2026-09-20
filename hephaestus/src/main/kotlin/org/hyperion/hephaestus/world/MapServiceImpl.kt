package org.hyperion.hephaestus.world

import org.bukkit.Bukkit
import org.bukkit.GameRules
import org.bukkit.World
import org.bukkit.WorldCreator
import org.bukkit.WorldType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerTeleportEvent
import org.bukkit.event.world.WorldUnloadEvent
import org.bukkit.plugin.Plugin

class MapServiceImpl(private val plugin: Plugin) : MapService, Listener {
    private val unloadedNameToMap: MutableList<String> = mutableListOf()
    private val nameToMap: MutableMap<String, Map> = mutableMapOf()
    override val maps: Collection<Map> get() = nameToMap.values

    override fun createMap(name: String, environment: World.Environment, type: WorldType, isEmpty: Boolean): Boolean {
        val worldCreator = WorldCreator(name)

        worldCreator.environment(environment)
        worldCreator.type(type)

        val world = worldCreator.createWorld() ?: return false

        world.setGameRule(GameRules.SPAWN_MOBS, false)
        world.setGameRule(GameRules.FIRE_DAMAGE, false)
        world.setGameRule(GameRules.ADVANCE_WEATHER, false)
        world.setGameRule(GameRules.RANDOM_TICK_SPEED, 0)
        world.setGameRule(GameRules.TNT_EXPLODES, false)
        world.setGameRule(GameRules.MOB_GRIEFING, false)
        world.setGameRule(GameRules.ADVANCE_TIME, false)

        return true
    }

    override fun loadMap(name: String, environment: World.Environment): Boolean {
        if  (!unloadedNameToMap.contains(name)) return false
        if (getMap(name) != null) return false

        val worldCreator = WorldCreator(name)
        worldCreator.environment(environment)
        val world = worldCreator.createWorld() ?: return false

        unloadedNameToMap.remove(name)
        nameToMap[name] = MapImpl(world, plugin, this)

        return true
    }

    override fun getMap(name: String): Map? {
        return nameToMap[name]
    }

    override fun getUnloadMaps(): Collection<String> {
        return unloadedNameToMap
    }

    fun onStart() {
        Bukkit.getWorlds().forEach { nameToMap[it.name] = MapImpl(it, plugin, this) }

        plugin.dataFolder.absoluteFile.parentFile.listFiles()?.filter { file ->
            file.isDirectory && (file.listFiles().any { it.name == "region" || it.name == "DIM1" || it.name == "DIM-1" })
        }?.forEach { unloadedNameToMap.add(it.name) }
    }

    @EventHandler
    fun WorldUnloadEvent.handle() {
        nameToMap.remove(world.name) ?: return
        unloadedNameToMap.add(world.name)
    }

    @EventHandler
    fun PlayerTeleportEvent.handle() {
        val map = getMap(to.world.name) ?: return
        isCancelled = !map.canJoin(player)
    }
}