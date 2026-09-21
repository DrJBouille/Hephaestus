package com.hephaestus.api.world.application

import com.hephaestus.api.world.application.exceptions.InvalidWorldException
import com.hephaestus.api.world.application.exceptions.WorldNotFoundException
import com.hephaestus.api.world.domain.World
import com.hephaestus.api.world.domain.WorldId
import com.hephaestus.api.world.domain.WorldRepository
import com.hephaestus.api.world.domain.WorldStorage
import java.io.InputStream
import org.springframework.stereotype.Service
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

@Service
class UploadWorld(
    private val repository: WorldRepository,
    private val storage: WorldStorage
) {
    private val UUID_REGEX = Regex("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}")

    fun execute(id: WorldId, file: InputStream) : World {
        val tempFile = Files.createTempFile("world-upload-", ".zip")
        try {
            Files.copy(file, tempFile, StandardCopyOption.REPLACE_EXISTING)

            Files.newInputStream(tempFile).use { validationStream ->
                ZipInputStream(validationStream).use { zip ->
                    var worldRoot: String? = null
                    var fileCount = 0
                    var hasLevelDat = false
                    var hasRegion = false

                    while (true) {
                        val entry = zip.nextEntry ?: break

                        val name = entry.name.replace('\\', '/').trimStart('/')
                        if (name.isBlank()) continue

                        if (entry.isDirectory || name.endsWith("/")) {
                            zip.closeEntry()
                            continue
                        }

                        fileCount++
                        if (fileCount > 100_000) throw InvalidWorldException("Too many files")

                        val root = name.substringBefore('/')
                        if (worldRoot == null) worldRoot = root
                        if (root != worldRoot) throw InvalidWorldException("The ZIP must contain exactly one world")

                        if (!isAllowedWorldEntry(entry, worldRoot)) throw InvalidWorldException("File is not allowed in a Minecraft world: $name")

                        if (name == "$worldRoot/level.dat" || name == "$worldRoot/uid.dat") hasLevelDat = true
                        if (name.startsWith("$worldRoot/region/") && name.endsWith(".mca")) hasRegion = true

                        zip.closeEntry()
                    }

                    if (!hasLevelDat) throw InvalidWorldException("The ZIP does not contain a Minecraft world (missing level.dat)")
                    if (!hasRegion) throw InvalidWorldException("The Minecraft world does not contain any region files")
                }
            }

            val world = repository.findById(id) ?: throw WorldNotFoundException(id)

            world.startUpload()

            val storageKey = Files.newInputStream(tempFile).use { uploadStream ->
                storage.store(worldId = id, input = uploadStream)
            }

            world.markUploaded()
            world.setStorageKey(storageKey)

            return repository.save(world)
        } finally {
            Files.deleteIfExists(tempFile)
        }
    }

    fun isAllowedWorldEntry(entry: ZipEntry, worldRoot: String): Boolean {
        val path = entry.name.replace('\\', '/').trimStart('/')

        val relative = path.removePrefix("$worldRoot/")

        if (relative == path) return false

        if (Path.of(relative).normalize() != Path.of(relative)) return false

        if (relative == "level.dat" || relative == "level.dat_old" || relative == "session.lock" || relative == "uid.dat" || relative == "uid.dat_old" || relative == "paper-world.yml") return true

        return when {
            relative.startsWith("region/") -> isMcaFile(relative)

            relative.startsWith("entities/") -> isMcaFile(relative)

            relative.startsWith("poi/") -> isMcaFile(relative)

            relative.startsWith("playerdata/") -> isUuidFile(relative, ".dat")

            relative.startsWith("advancements/") -> isUuidFile(relative, ".json")

            relative.startsWith("stats/") -> isUuidFile(relative, ".json")

            relative.startsWith("data/") -> isDataFile(relative)

            else -> false
        }
    }

    private fun isMcaFile(path: String): Boolean {
        val fileName = path.substringAfterLast('/')
        return fileName.matches(Regex("r\\.-?\\d+\\.-?\\d+\\.mca"))
    }

    private fun isUuidFile(path: String, extension: String): Boolean {
        val fileName = path.substringAfterLast('/')

        return fileName.matches(Regex("${UUID_REGEX.pattern}$extension"))
    }

    private fun isDataFile(path: String): Boolean {
        val fileName = path.substringAfterLast('/')

        return fileName.endsWith(".dat") || fileName.endsWith(".dat_old") || fileName.endsWith(".json") || fileName.endsWith(".nbt")
    }
}