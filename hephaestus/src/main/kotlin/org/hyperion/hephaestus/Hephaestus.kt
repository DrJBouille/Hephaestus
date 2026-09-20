package org.hyperion.hephaestus

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.hyperion.hephaestus.commands.WorldCommand
import org.hyperion.hephaestus.services.ApiClient
import org.hyperion.hephaestus.services.worlds.WorldServiceImpl
import org.hyperion.hephaestus.world.MapServiceImpl

class Hephaestus : JavaPlugin() {

    override fun onEnable() {
        logger.info("Loading Hephaestus")

        val baseUrl = System.getenv("HEPHAESTUS_API_URL")
            ?: config.getString("api.base-url")
            ?: "http://127.0.0.1:9003"

        val apiKey = System.getenv("HEPHAESTUS_PLUGIN_KEY")
            ?: config.getString("api.key")?.takeIf { it != "change-me" }
            ?: run {
                logger.severe("Clé d'API manquante : définis HEPHAESTUS_PLUGIN_KEY ou api.key dans config.yml")
                server.pluginManager.disablePlugin(this)
                return
            }
        val apiClient = ApiClient(baseUrl, apiKey)

        val worldService = WorldServiceImpl(apiClient)

        val mapService = MapServiceImpl(this)
        Bukkit.getPluginManager().registerEvents(mapService, this)
        mapService.onStart()

        registerCommand("map", WorldCommand(this, worldService))
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
