package org.hyperion.hephaestus.utils

import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.plugin.Plugin
import org.hyperion.hephaestus.exceptions.ApiException
import java.util.concurrent.CompletableFuture
import java.util.logging.Level

object AsyncUtils {
    fun <T> handle(plugin: Plugin, future: CompletableFuture<T>, sender: CommandSender, onSuccess: (T) -> Unit) {
        future.thenAccept { result ->
                Bukkit.getScheduler().runTask(plugin , Runnable { onSuccess(result) })
            }.exceptionally { ex ->
                Bukkit.getScheduler().runTask(plugin , Runnable {
                    plugin.logger.log(Level.WARNING, "Erreur async: ", ex)
                    Messages.error(sender, extractErrorMessage(ex))
                })

                null
            }
    }

    private fun extractErrorMessage(ex: Throwable): String {
        val cause = ex.cause ?: ex

        return if (cause is ApiException) {
            cause.detail
        } else {
            "Une erreur est survenue: ${cause.message}"
        }
    }
}