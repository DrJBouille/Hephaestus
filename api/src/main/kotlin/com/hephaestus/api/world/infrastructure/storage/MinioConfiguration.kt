package com.hephaestus.api.world.infrastructure.storage

import io.minio.MinioClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MinioConfiguration {
    @Bean
    fun minioClient(properties: MinioProperties): MinioClient = MinioClient.builder()
        .endpoint(properties.endpoint)
        .credentials(
            properties.accessKey,
            properties.secretKey
        )
        .build()

}