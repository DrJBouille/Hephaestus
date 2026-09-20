package org.hyperion.hephaestus.services.worlds

import org.hyperion.hephaestus.entities.PageResult
import org.hyperion.hephaestus.services.ApiClient
import java.util.concurrent.CompletableFuture

class WorldServiceImpl(private val client: ApiClient) : WorldService {
    private val BASE = "/api/worlds"

    override fun getWorlds(): CompletableFuture<PageResult> {
        return client.get<PageResult>(BASE, PageResult::class.java)
            .exceptionally { error ->
                println("Erreur lors de la récupération des mondes : ${error.message}")
                PageResult(emptyList(), 0, 0, 0, 0)
            }
    }

}