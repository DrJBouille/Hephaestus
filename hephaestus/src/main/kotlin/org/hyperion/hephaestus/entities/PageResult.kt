package org.hyperion.hephaestus.entities

data class PageResult(
    val content: List<WorldDto>,
    val page: Int,
    val size: Int,
    val totalElements: Int,
    val totalPages: Int
)
