package com.hephaestus.api.world.application

import com.hephaestus.api.world.application.data.DownloadedWorld
import com.hephaestus.api.world.application.exceptions.WorldNotFoundException
import com.hephaestus.api.world.application.exceptions.WorldNotUploadedException
import com.hephaestus.api.world.domain.WorldId
import com.hephaestus.api.world.domain.WorldRepository
import com.hephaestus.api.world.domain.WorldStorage
import org.springframework.stereotype.Service

@Service
class DownloadWorld(
    private val repository: WorldRepository,
    private val storage: WorldStorage
) {
    fun execute(id: WorldId) : DownloadedWorld {
        val world = repository.findById(id) ?: throw WorldNotFoundException(id)

        val storageKey = world.storageKey ?: throw WorldNotUploadedException()

        return DownloadedWorld(
            filename = "${world.name}.zip",
            content = storage.retrieve(storageKey)
        )
    }
}

