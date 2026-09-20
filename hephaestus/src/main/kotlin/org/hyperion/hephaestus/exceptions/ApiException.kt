package org.hyperion.hephaestus.exceptions

class ApiException(
    val statusCode: Int,
    val detail: String,
) : RuntimeException(detail)