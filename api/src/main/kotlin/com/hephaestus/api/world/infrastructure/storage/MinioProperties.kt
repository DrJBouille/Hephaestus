package com.hephaestus.api.world.infrastructure.storage

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("minio")
data class MinioProperties(
    val endpoint: String,
    val accessKey: String,
    val secretKey: String,
    val bucket: String,
)
