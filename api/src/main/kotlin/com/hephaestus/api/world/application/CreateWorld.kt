package com.hephaestus.api.world.application

import com.hephaestus.api.world.domain.World
import com.hephaestus.api.world.domain.WorldRepository
import org.springframework.stereotype.Service

@Service
class CreateWorld(private val repository: WorldRepository) {
    fun execute(name: String): World {
        val world = World.create(name)

        return repository.save(world)
    }
}