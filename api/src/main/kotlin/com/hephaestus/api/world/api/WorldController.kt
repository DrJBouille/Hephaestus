package com.hephaestus.api.world.api

import com.hephaestus.api.world.api.dto.CreateWorldRequestDto
import com.hephaestus.api.world.api.dto.WorldResponse
import com.hephaestus.api.world.application.ArchiveWorld
import com.hephaestus.api.world.application.CreateWorld
import com.hephaestus.api.world.application.DownloadWorld
import com.hephaestus.api.world.application.GetWorld
import com.hephaestus.api.world.application.GetWorlds
import com.hephaestus.api.world.application.UploadWorld
import com.hephaestus.api.world.domain.PageResult
import com.hephaestus.api.world.domain.World
import com.hephaestus.api.world.domain.WorldId
import jakarta.validation.Valid
import org.springframework.core.io.InputStreamResource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@RestController
@RequestMapping("/api/worlds")
class WorldController (
    private val createWorld: CreateWorld,
    private val getWorld: GetWorld,
    private val archiveWorld: ArchiveWorld,
    private val uploadWorld: UploadWorld,
    private val downloadWorld: DownloadWorld,
    private val getWorlds: GetWorlds,
){

    @PostMapping
    fun create(@Valid @RequestBody request: CreateWorldRequestDto) : WorldResponse {
        return createWorld.execute(
            name = request.name,
            environment = request.environment,
            worldType = request.worldType,
        ).toResponse()
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: UUID) : WorldResponse {
        return getWorld.execute(WorldId(id)).toResponse()
    }

    @GetMapping
    fun list(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int,
    ) : PageResult<WorldResponse> {
        return getWorlds.execute(page, size).map { it.toResponse() }
    }

    @PostMapping("/archive/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun archive(@PathVariable id: UUID) {
        archiveWorld.execute(WorldId(id))
    }

    @PostMapping("/upload/{id}")
    fun upload(@PathVariable id: UUID, @RequestPart("file") file: MultipartFile) : WorldResponse {
        println(file)
        return uploadWorld.execute(
            id = WorldId(id),
            file = file.inputStream
        ).toResponse()
    }

    @GetMapping("/download/{id}")
    fun download(@PathVariable id: UUID) : ResponseEntity<InputStreamResource> {
        val world = downloadWorld.execute(WorldId(id))

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .header(
                HttpHeaders.CONTENT_DISPOSITION,
                "attachement; filename=\"${world.filename}\""
            )
            .body(InputStreamResource(world.content))
    }

    private fun World.toResponse() = WorldResponse(
        id = id.value,
        name = name,
        environment = environment,
        worldType = worldType,
        status = status,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}