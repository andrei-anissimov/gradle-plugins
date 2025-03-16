import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

tasks.register("sortFiles") {
    group = "files"
    description = """
        Sorts files in the given directory into build/files subdirectories based on the sorting type.
        Supported sorting types: [timestamp,extension] 
        """.trimIndent()

    val filesFolder = findProperty("plugin.files.folder")
    val filesSortType = findProperty("plugin.files.sortType")
    val filesPluginVersion = findProperty("plugin.files.version")

    logger.info("==================== Sort Files Task ====================")
    logger.info("Properties:")
    logger.info("plugin.files.folder: $filesFolder")
    logger.info("plugin.files.sortType: $filesSortType")
    logger.info("plugin.files.version: $filesPluginVersion")
    logger.info("=========================================================")

    val projectDir = layout.projectDirectory
    val buildDir = layout.buildDirectory

    val inputDir = projectDir.dir("$filesFolder")

    val keySelector : (File) -> String = when(filesSortType) {
        "alphabet" -> SORT_ALPHABETICALLY
        "date" ->  {file -> creationDate(file.lastModified()) }
        "extension" -> { file -> file.extension }
        else -> {
            logger.error("Unknown sorting type: $filesSortType")
            throw RuntimeException("Unknown sorting type: $filesSortType")
        }
    }

    val groupedFiles = inputDir.asFileTree.files.groupBy { keySelector.invoke(it) }

    groupedFiles.forEach { (key, files) ->
            files.forEach { file ->
                val dest = buildDir.dir("$filesFolder/$key/${file.name}").get().asFile.toPath()
                Files.createDirectories(dest.parent)
                Files.copy(file.toPath(), dest, StandardCopyOption.REPLACE_EXISTING)
//            copy {
//                from(file)
//                into(buildDir.dir("$filesFolder/$key"))
//            }
            }
    }
}

tasks.register<Delete>("cleanFiles") {
    group = "files"
    description = "clean build/files directory"

    logger.info("==================== Clean Files Task ====================")
    val filesFolder = project.findProperty("tasks.files.folder")
    val outputDir = layout.buildDirectory.dir("$filesFolder")
    delete(outputDir)
    logger.info("==========================================================")
}

val SORT_ALPHABETICALLY: (File) -> String = { file -> file.name[0].lowercase() }
val SORT_BY_CREATION_DATE: (File) -> String = { file -> creationDate(file.lastModified()) }
val SORT_BY_EXTENTION: (File) -> String = { file -> file.extension }


private fun creationDate(lastModified: Long): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return Instant.ofEpochMilli(lastModified)
            .atZone(ZoneId.systemDefault())
            .format(formatter)
}