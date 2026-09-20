package org.hyperion.hephaestus.world

import org.bukkit.World.Environment
import org.bukkit.WorldType

interface MapService {
    val maps: Collection<Map>
    fun createMap(name: String, environment: Environment, type: WorldType, isEmpty: Boolean): Boolean
    fun loadMap(name: String, environment: Environment): Boolean
    fun getMap(name: String): Map?
    fun getUnloadMaps(): Collection<String>
}