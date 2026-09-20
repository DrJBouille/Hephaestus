package com.hephaestus.api.authentication.infrastructure.persistence

import com.hephaestus.api.authentication.domain.Authority
import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "users")
class UserEntity (
    @Id
    val id: UUID,

    @Column(nullable = false, unique = true, length = 16)
    var username: String,

    @Column(nullable = false)
    var passwordHash: String,

    @Column(nullable = false)
    var enabled: Boolean,

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "user_authorities",
        joinColumns = [JoinColumn(name = "user_id")]
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "authority", nullable = false)
    var authorities: MutableSet<Authority> = mutableSetOf(),
)