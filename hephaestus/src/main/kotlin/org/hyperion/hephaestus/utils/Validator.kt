package org.hyperion.hephaestus.utils

import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.math.BigDecimal

object Validator {
    fun player(sender: CommandSender, value: String): Player? {
        val player = Bukkit.getPlayer(value)

        if (player == null) {
            Errors.playerNotFound(sender, value)
            return null
        }

        return player
    }

    fun amount(sender: CommandSender, value: String): BigDecimal? {
        val amount = value.toBigDecimalOrNull()

        if (amount == null || amount <= BigDecimal.ZERO) {
            Errors.invalidAmount(sender, value)
            return null
        }

        return amount
    }
}