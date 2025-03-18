package io.anisand.plugin.files

import org.gradle.api.Project

class PluginProperties(project: Project) {

    val filesFolder: String = project.findProperty("plugin.files.folder").toString()

    val filesSortType: String = project.findProperty("plugin.files.sortType").toString()
    override fun toString(): String {
        return "PluginProperties(filesFolder='$filesFolder', filesSortType='$filesSortType')"
    }


}