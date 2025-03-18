package io.anisand.plugin.files.tasks

import io.anisand.plugin.files.PluginProperties
import io.anisand.plugin.files.sorting.SortType
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.nio.file.Files
import java.nio.file.StandardCopyOption

class SortFilesTask : DefaultTask() {

    override fun getGroup(): String {
        return "files"
    }

    override fun getDescription(): String {
        return """
            Sorts files in the given directory into build/files subdirectories based on the sorting type.
            Supported sorting types: [timestamp,extension] 
        """.trimIndent()
    }

    @TaskAction
    fun action() {
        val properties = PluginProperties(project)

        logger.info("==================== Sort Files Task ====================")
        logger.info("properties: $properties")
        logger.info("=========================================================")

        val projectDir = project.layout.projectDirectory
        val buildDir = project.layout.buildDirectory

        val inputDir = projectDir.dir(properties.filesFolder)

        val sortType : SortType = when(properties.filesSortType) {
            "alphabet" -> SortType.ALPHABET
            "date" ->  SortType.DATE
            "extension" -> SortType.EXTENSION
            else -> {
                throw RuntimeException("Unknown sorting type: $properties.filesSortType")
            }
        }

        val groupedFiles = inputDir.asFileTree.files.groupBy { sortType.keySelector.invoke(it) }

        groupedFiles.forEach { (key, files) ->
            files.forEach { file ->
                val dest = buildDir.dir("${properties.filesFolder}/$key/${file.name}").get().asFile.toPath()
                Files.createDirectories(dest.parent)
                Files.copy(file.toPath(), dest, StandardCopyOption.REPLACE_EXISTING)
            }
        }
    }
}
