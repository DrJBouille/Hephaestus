package org.hyperion.hephaestus.commands

import io.papermc.paper.command.brigadier.BasicCommand
import io.papermc.paper.command.brigadier.CommandSourceStack
import org.bukkit.plugin.Plugin
import org.hyperion.hephaestus.services.worlds.WorldService
import org.hyperion.hephaestus.utils.AsyncUtils

class WorldCommand(private val plugin: Plugin, private val worldService: WorldService) : BasicCommand {
    override fun execute(sender: CommandSourceStack, args: Array<out String>
    ) {
        AsyncUtils.handle(
            plugin,
            worldService.getWorlds(),
            sender.sender
        ) { pageResult ->
            pageResult.content.forEach { world ->
                sender.sender.sendMessage("§7- ${world.name}")
            }
        }
    }
}