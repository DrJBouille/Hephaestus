package com.hephaestus.api.world.application

import com.hephaestus.api.world.domain.PageResult
import com.hephaestus.api.world.domain.World
import com.hephaestus.api.world.domain.WorldRepository
import org.springframework.stereotype.Service

@Service
class GetWorlds(private val repository: WorldRepository) {
    fun execute(page: Int, size: Int): PageResult<World> {
        require(page >= 0) { "Page must be greater than or equal to 0" }
        require(size in 1..100) { "Size must be between 1 and 100" }

        return repository.findAll(page, size)
    }
}