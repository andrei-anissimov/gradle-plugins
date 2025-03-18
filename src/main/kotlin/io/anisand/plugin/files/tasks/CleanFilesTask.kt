package io.anisand.plugin.files.tasks

import io.anisand.plugin.files.PluginProperties
import org.gradle.api.tasks.Delete
import org.gradle.api.tasks.TaskAction

class CleanFilesTask : Delete() {

    override fun getGroup(): String {
        return "files"
    }

    override fun getDescription(): String {
        return "clean build/files directory"
    }

    @TaskAction
    fun action() {
        val properties = PluginProperties(project)

        logger.info("==================== Clean Files Task ====================")
        logger.info("files folder: ${properties.filesFolder}")
        logger.info("==========================================================")

        val outputDir = project.layout.buildDirectory.dir(properties.filesFolder)
        delete(outputDir)
    }
}