package com.hephaestus.api.world.infrastructure.storage

import com.hephaestus.api.world.domain.StorageKey
import com.hephaestus.api.world.domain.WorldId
import com.hephaestus.api.world.domain.WorldStorage
import io.minio.BucketExistsArgs
import io.minio.GetObjectArgs
import io.minio.MakeBucketArgs
import io.minio.MinioClient
import io.minio.PutObjectArgs
import io.minio.RemoveObjectArgs
import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.io.InputStream

@Component
class MinioWorldStorage(
    private val minio: MinioClient,
    private val properties: MinioProperties
) : WorldStorage {

    private val logger = LoggerFactory.getLogger(MinioWorldStorage::class.java)

    @PostConstruct
    fun init() {
        ensureBucket()
    }

    override fun store(
        worldId: WorldId,
        input: InputStream
    ): StorageKey {
        val key = "worlds/${worldId.value}.zip"

        minio.putObject(
            PutObjectArgs.builder()
                .bucket(properties.bucket)
                .`object`(key)
                .stream(input, -1, 10 * 1024 * 1024)
                .contentType("application/zip")
                .build()
        )

        return StorageKey(key)
    }

    override fun retrieve(key: StorageKey): InputStream = minio.getObject(
            GetObjectArgs.builder()
                .bucket(properties.bucket)
                .`object`(key.value)
                .build()
        )


    override fun delete(key: StorageKey) {
        minio.removeObject(
            RemoveObjectArgs.builder()
                .bucket(properties.bucket)
                .`object`(key.value)
                .build()
        )
    }

    private fun ensureBucket() {
        val exists = minio.bucketExists(BucketExistsArgs.builder().bucket(properties.bucket).build())

        if (!exists) {
            minio.makeBucket(MakeBucketArgs.builder().bucket(properties.bucket).build())
            logger.info("Created MinIO bucket '{}'", properties.bucket)
        } else {
            logger.debug("MinIO bucket '{}' already exists", properties.bucket)
        }
    }
}