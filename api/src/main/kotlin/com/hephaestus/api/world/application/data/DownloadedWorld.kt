package com.hephaestus.api.world.application.data

import java.io.InputStream

data class DownloadedWorld(
    val filename: String,
    val content: InputStream,
)
