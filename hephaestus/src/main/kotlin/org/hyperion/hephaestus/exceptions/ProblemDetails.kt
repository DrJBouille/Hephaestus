package org.hyperion.hephaestus.exceptions

data class ProblemDetails(
    val type: String,
    val title: String,
    val detail: String,
    val instance: String,
    val traceId: String
)
