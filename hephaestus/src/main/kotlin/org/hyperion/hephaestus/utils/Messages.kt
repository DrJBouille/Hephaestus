package org.hyperion.hephaestus.utils

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.CommandSender

object Messages {
    private val prefix = Component.text()
        .append(Component.text("[", NamedTextColor.GOLD))
        .append(Component.text("Hyperion", NamedTextColor.WHITE))
        .append(Component.text("] ", NamedTextColor.GOLD))
        .build()

    fun error(sender: CommandSender, vararg messages: String) {
        sender.sendMessage(message(*messages).color(NamedTextColor.RED))
    }

    fun success(sender: CommandSender, vararg messages: String) {
        sender.sendMessage(message(*messages).color(NamedTextColor.GREEN))
    }

    fun info(sender: CommandSender, vararg messages: String) {
        sender.sendMessage(message(*messages).color(NamedTextColor.BLUE))
    }

    private fun message(vararg messages: String): Component {
        var result = prefix

        messages.forEachIndexed { index, message ->
            result = result.append(Component.text(message))

            if (index < messages.lastIndex) {
                result = result.appendNewline()
            }
        }

        return result
    }
}