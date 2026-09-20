package com.hephaestus.api.world.domain

import java.io.InputStream

interface WorldStorage {
    fun store(worldId: WorldId, input: InputStream) : StorageKey
    fun retrieve(key: StorageKey) : InputStream
    fun delete(key: StorageKey)
}