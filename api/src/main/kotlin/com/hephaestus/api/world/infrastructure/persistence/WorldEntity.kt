package com.hephaestus.api.world.infrastructure.persistence

import com.hephaestus.api.world.domain.WorldStatus
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "worlds")
class WorldEntity (
    @Id
    val id: UUID,

    @Column(nullable = false)
    var name: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: WorldStatus,

    @Column(nullable = false)
    val createdAt: Instant,

    @Column(nullable = false)
    val updatedAt: Instant,

    @Column
    var storageKey: String?
)