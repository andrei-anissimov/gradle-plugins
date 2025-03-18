package io.anisand.plugin.files

import io.anisand.plugin.files.tasks.CleanFilesTask
import io.anisand.plugin.files.tasks.SortFilesTask
import org.gradle.api.Plugin
import org.gradle.api.Project

class FilesPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.tasks.register("sortFiles", SortFilesTask::class.java)
        project.tasks.register("cleanFiles", CleanFilesTask::class.java)
    }
}
