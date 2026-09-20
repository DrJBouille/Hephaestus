package org.hyperion.hephaestus.utils

import org.bukkit.command.CommandSender

object Errors {
    fun playerNotFound(sender: CommandSender, value: String) {
        Messages.error(sender, "Joueur introuvable : $value")
    }

    fun invalidAmount(sender: CommandSender, value: String) {
        Messages.error(sender, "Montant invalide : $value")
    }
}