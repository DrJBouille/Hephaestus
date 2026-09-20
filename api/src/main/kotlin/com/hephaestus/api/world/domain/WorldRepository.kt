package com.hephaestus.api.world.domain

interface WorldRepository {
    fun findById(id: WorldId): World?
    fun findAll(page: Int, size: Int): PageResult<World>
    fun save(world: World): World
    fun delete(world: World)
}