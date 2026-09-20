package com.hephaestus.api.world.domain

import java.time.Instant

class World private constructor(
    val id: WorldId,
    name: String,
    val createdAt: Instant,
    status: WorldStatus = WorldStatus.CREATED,
    updatedAt: Instant = Instant.now(),
    storageKey: StorageKey? = null,
) {
    var name: String = name
        private set

    var status: WorldStatus = status
        private set

    var updatedAt: Instant = updatedAt
        private set

    var storageKey: StorageKey? = storageKey
        private set

    private val _events = mutableListOf<WorldEvent>()

    val events: List<WorldEvent>
        get() = _events.toList()

    fun rename(name: String) {
        require(name.isNotBlank()) { "Name must not be blank" }

        this.name = name
        touch()
    }

    fun setStorageKey(storageKey: StorageKey) {
        require(name.isNotBlank()) { "Key must not be blank" }

        this.storageKey = storageKey
        touch()
    }

    fun markUploaded() {
        status = WorldStatus.AVAILABLE
        touch()

        _events += WorldUploaded(id)
    }

    fun archive() {
        check(status == WorldStatus.AVAILABLE) { "Only available worlds can be archived" }

        status = WorldStatus.ARCHIVED
        touch()

        _events += WorldArchived(id)
    }

    fun startUpload() {
        check(status == WorldStatus.CREATED) { "Only created worlds can be uploaded" }

        status = WorldStatus.UPLOADING
        touch()
    }

    fun clearEvents() {
        _events.clear()
    }

    private fun touch() {
        updatedAt = Instant.now()
    }

    companion object {
        fun create(name: String) : World {
            val now = Instant.now()

            return World(id = WorldId.generate(), name = name, createdAt = now)
        }

        fun reconstitute(
            id: WorldId,
            name: String,
            status: WorldStatus,
            createdAt: Instant,
            updatedAt: Instant,
            storageKey: StorageKey? = null,
        ): World {
            return World(
                id = id,
                name = name,
                status = status,
                createdAt = createdAt,
                updatedAt = updatedAt,
                storageKey = storageKey,
            )
        }
    }
}