package com.hephaestus.api.world.application.exceptions

import com.hephaestus.api.world.domain.WorldId

class WorldNotFoundException(id: WorldId) : RuntimeException("World with id $id not found")