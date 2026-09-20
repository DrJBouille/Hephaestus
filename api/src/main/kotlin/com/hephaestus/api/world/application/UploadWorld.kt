package com.hephaestus.api.world.application

import com.hephaestus.api.world.application.exceptions.WorldNotFoundException
import com.hephaestus.api.world.domain.World
import com.hephaestus.api.world.domain.WorldId
import com.hephaestus.api.world.domain.WorldRepository
import com.hephaestus.api.world.domain.WorldStorage
import java.io.InputStream
import org.springframework.stereotype.Service

@Service
class UploadWorld(
    private val repository: WorldRepository,
    private val storage: WorldStorage
) {
    fun execute(id: WorldId, file: InputStream) : World {
        val world = repository.findById(id) ?: throw WorldNotFoundException(id)

        world.startUpload()

        val storageKey = storage.store(worldId = id, input = file)

        world.markUploaded()
        world.setStorageKey(storageKey)

        return repository.save(world)
    }
}