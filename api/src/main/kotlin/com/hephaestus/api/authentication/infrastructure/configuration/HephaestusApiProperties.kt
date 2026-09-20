package com.hephaestus.api.authentication.infrastructure.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("hephaestus.api")
data class HephaestusApiProperties(
    val pluginKey: String
)