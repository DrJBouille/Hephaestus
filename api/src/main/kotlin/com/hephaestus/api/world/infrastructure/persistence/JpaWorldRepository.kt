package com.hephaestus.api.world.infrastructure.persistence

import com.hephaestus.api.world.domain.PageResult
import com.hephaestus.api.world.domain.StorageKey
import com.hephaestus.api.world.domain.World
import com.hephaestus.api.world.domain.WorldId
import com.hephaestus.api.world.domain.WorldRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository

@Repository
class JpaWorldRepository(private val repository : SpringDataWorldRepository) : WorldRepository {
    override fun findById(id: WorldId): World? = repository.findById(id.value).orElse(null)?.toDomain()

    override fun findAll(page: Int, size: Int): PageResult<World> {
        val result = repository.findAll(PageRequest.of(page, size))

        return PageResult(
            content = result.content.map { it.toDomain() },
            page = result.number,
            size = result.size,
            totalElements = result.totalElements,
            totalPages = result.totalPages
        )
    }

    override fun save(world: World): World = repository.save(world.toEntity()).toDomain()

    override fun delete(world: World) = repository.deleteById(world.id.value)


    private fun WorldEntity.toDomain() : World = World.reconstitute(
            id = WorldId(id),
            name = name,
            status = status,
            createdAt = createdAt,
            updatedAt = updatedAt,
            storageKey = storageKey?.let { StorageKey(it) }
        )

    private fun World.toEntity() : WorldEntity = WorldEntity(
            id = id.value,
            name = name,
            status = status,
            createdAt = createdAt,
            updatedAt = updatedAt,
            storageKey = storageKey?.value
        )
}