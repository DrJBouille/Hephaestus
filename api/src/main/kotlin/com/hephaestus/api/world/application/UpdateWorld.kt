package com.hephaestus.api.world.application

import com.hephaestus.api.world.application.exceptions.WorldNotFoundException
import com.hephaestus.api.world.domain.World
import com.hephaestus.api.world.domain.WorldId
import com.hephaestus.api.world.domain.WorldRepository
import org.springframework.stereotype.Service

@Service
class UpdateWorld(private val repository: WorldRepository) {
    fun execute(id: WorldId, name: String): World {
        val world = repository.findById(id) ?: throw WorldNotFoundException(id)

        name.let(world::rename)

        return repository.save(world)
    }
}