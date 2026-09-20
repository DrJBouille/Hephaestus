package com.hephaestus.api.world.infrastructure.persistence

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface SpringDataWorldRepository : CrudRepository<WorldEntity, UUID> {
    fun findAll(pageable: Pageable): Page<WorldEntity>
}