package com.stash.metalottie.plugin

import com.stash.metalottie.plugin.tasks.generator.MetadataLottieGeneratorTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Property

interface MetadataLottiePluginExtension {
    val themeFile: Property<String>
}

class MetaLottiePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create(
            "metalottie",
            MetadataLottiePluginExtension::class.java
        )

        project.tasks.register(
            "createMetaLottie",
            MetadataLottieGeneratorTask::class.java
        ) { task ->
            task.themeFile = extension.themeFile.getOrElse("")
        }
    }
}
