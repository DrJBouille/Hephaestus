package com.hephaestus.api.authentication.infrastructure.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("hephaestus.jwt")
class JwtProperties(
    val secret: String,
    val expiration: Long
)
