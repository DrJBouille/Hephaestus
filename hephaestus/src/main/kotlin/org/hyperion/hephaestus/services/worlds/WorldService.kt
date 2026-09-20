package org.hyperion.hephaestus.services.worlds

import org.hyperion.hephaestus.entities.PageResult
import java.util.concurrent.CompletableFuture

interface WorldService {
    fun getWorlds() : CompletableFuture<PageResult>
}