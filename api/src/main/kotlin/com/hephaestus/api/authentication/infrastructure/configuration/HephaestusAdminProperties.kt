package com.hephaestus.api.authentication.infrastructure.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("hephaestus.admin")
data class HephaestusAdminProperties(
    val username: String,
    val password: String
)