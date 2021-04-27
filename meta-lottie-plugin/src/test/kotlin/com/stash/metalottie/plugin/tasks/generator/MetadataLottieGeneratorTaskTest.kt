package com.stash.metalottie.plugin.tasks.generator

import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException
import org.gradle.testkit.runner.TaskOutcome.SUCCESS
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue


class MetadataLottieGeneratorTaskTest {
    @TempDir
    lateinit var testProjectDir: File
    lateinit var settingsFile: File
    lateinit var buildFile: File

    @BeforeEach
    fun setup() {
        settingsFile = File(testProjectDir, "settings.gradle")
        buildFile = File(testProjectDir, "build.gradle")
    }

    @Test
    fun testHelloWorldTask() {
        settingsFile!!.writeText("rootProject.name = 'hello-world'")
        val buildFileContent = "task helloWorld {" +
                "    doLast {" +
                "        println 'Hello world!'" +
                "    }" +
                "}"
        buildFile.writeText(buildFileContent)

        val result: BuildResult = GradleRunner.create()
            .withProjectDir(testProjectDir)
            .withArguments("helloWorld")
            .build()
        assertTrue(result.output.contains("Hello world!"))
        assertEquals(SUCCESS, result.task(":helloWorld")?.outcome)
    }

//    @Test
//    fun `kewltest`() {
//
//    }
}