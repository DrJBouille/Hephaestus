package com.hephaestus.api.world.application

import com.hephaestus.api.world.domain.Environment
import com.hephaestus.api.world.domain.World
import com.hephaestus.api.world.domain.WorldRepository
import com.hephaestus.api.world.domain.WorldType
import org.springframework.stereotype.Service

@Service
class CreateWorld(private val repository: WorldRepository) {
    fun execute(name: String, environment: Environment, worldType: WorldType): World {
        val world = World.create(name, environment, worldType)

        return repository.save(world)
    }
}