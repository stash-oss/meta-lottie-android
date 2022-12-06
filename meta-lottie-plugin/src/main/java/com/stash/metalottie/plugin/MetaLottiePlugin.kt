package com.stash.metalottie.plugin

import com.stash.metalottie.plugin.tasks.generator.ComposeDynamicPropertiesGeneratorTask
import com.stash.metalottie.plugin.tasks.generator.ComposeThemeGeneratorTask
import com.stash.metalottie.plugin.tasks.generator.ResourceMetadataGeneratorTask
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
            ResourceMetadataGeneratorTask::class.java
        ) { task ->
            task.themeFile = extension.themeFile.getOrElse("")
        }

        project.tasks.register(
            "createMetaLottieCompose",
            ComposeDynamicPropertiesGeneratorTask::class.java
        ) { task ->
            task.themeFile = extension.themeFile.getOrElse("")
        }

        project.tasks.register(
            "createMetaLottieComposeTheme",
            ComposeThemeGeneratorTask::class.java
        ) { task ->
            task.themeFile = extension.themeFile.getOrElse("")
        }
    }
}
