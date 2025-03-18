package io.anisand.plugin.files.sorting

import io.anisand.plugin.files.utils.FileUtils
import java.io.File

enum class SortType(val keySelector: (File) -> String) {
    ALPHABET(
        { file -> file.name[0].lowercase() }
    ),
    DATE(
        { file -> FileUtils.lastModifiedDate(file.lastModified()) }
    ),
    EXTENSION(
        { file -> file.extension }
    )
}