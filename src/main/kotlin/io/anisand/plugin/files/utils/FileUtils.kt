package io.anisand.plugin.files.utils

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object FileUtils {
    fun lastModifiedDate(lastModified: Long): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return Instant.ofEpochMilli(lastModified)
            .atZone(ZoneId.systemDefault())
            .format(formatter)
    }
}