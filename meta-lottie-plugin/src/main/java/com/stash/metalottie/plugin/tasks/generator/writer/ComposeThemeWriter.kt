package com.stash.metalottie.plugin.tasks.generator.writer

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asClassName
import com.stash.metalottie.plugin.tasks.generator.model.theme.LottieInputThemeMap
import com.stash.metalottie.plugin.tasks.generator.model.theme.LottieInputThemeMapItem
import java.io.File
import java.util.Locale

class ComposeThemeWriter {
    companion object {
        private val SrcDirNamespaceRegex =
            "src${File.separator}main${File.separator}(?:java|kotlin)${File.separator}".toRegex()

        private val composableClassName = ClassName(
            "androidx.compose.runtime",
            "Composable"
        )
        private val themeColorProviderClassName = ClassName(
            "com.stash.metalottie.compose",
            "ThemeColorProvider"
        )
        private val colorValueIdentifierClassName = ClassName(
            "com.stash.metalottie.compose",
            "ColorValueIdentifier"
        )
        private val colorTypeClassName = ClassName(
            "com.stash.metalottie.compose",
            "ColorType"
        )
    }

    fun write(
        themeMap: LottieInputThemeMap,
        name: String,
        outDirectory: File
    ) {
        val startIndex = SrcDirNamespaceRegex.find(outDirectory.absolutePath)
            ?: throw Exception("Could not determine the namespace from source directory given: ${outDirectory.absolutePath}")
        val namespace = outDirectory.absolutePath
            .substring(startIndex.range.last + 1)
            .replace(File.separator, ".")
        val sourceDirectory =
            File(outDirectory.absolutePath.substring(0, startIndex.range.last + 1))

        buildFileSpec(themeMap, name, namespace).writeTo(sourceDirectory)
    }

    fun write(
        themeMap: LottieInputThemeMap,
        name: String,
        namespace: String,
        out: Appendable
    ) {
        buildFileSpec(themeMap, name, namespace).writeTo(out)
    }

    private fun buildFileSpec(
        themeMap: LottieInputThemeMap,
        name: String,
        namespace: String
    ): FileSpec {
        val capitalizedName = name.replaceFirstChar {
            if (it.isLowerCase()) {
                it.titlecase(Locale.getDefault())
            } else {
                it.toString()
            }
        }

        return FileSpec
            .builder(namespace, capitalizedName)
            .addType(
                TypeSpec.objectBuilder(name)
                    .addSuperinterface(themeColorProviderClassName)
                    .addFunction(buildGetValueFunction())
                    .addFunction(buildColorValueFunction("Stroke", themeMap.strokeColors))
                    .addFunction(buildColorValueFunction("Fill", themeMap.fillColors))
                    .build()
            )
            .build()
    }

    private fun buildGetValueFunction(): FunSpec {
        return FunSpec.builder("getValue")
            .addAnnotation(composableClassName)
            .addModifiers(KModifier.OVERRIDE)
            .addParameter(
                ParameterSpec.builder(
                    "identifier",
                    colorValueIdentifierClassName
                ).build()
            )
            .returns(Int::class.asClassName().copy(nullable = true))
            .beginControlFlow("return when (identifier.type) {")
            .addStatement(
                "%T.STROKE -> getStrokeColorValue(key = identifier.key)",
                colorTypeClassName
            )
            .addStatement(
                "%T.FILL -> getFillColorValue(key = identifier.key)",
                colorTypeClassName
            )
            .endControlFlow()
            .build()
    }

    private fun buildColorValueFunction(
        type: String,
        colors: List<LottieInputThemeMapItem>
    ): FunSpec {
        return FunSpec.builder("get${type}ColorValue")
            .addAnnotation(composableClassName)
            .addModifiers(KModifier.PRIVATE)
            .addParameter(
                ParameterSpec.builder(
                    "key",
                    String::class
                ).build()
            )
            .returns(Int::class.asClassName().copy(nullable = true))
            .beginControlFlow("return when (key) {")
            .apply {
                colors
                    .map { it.name }
                    .toSet()
                    .forEach { item ->
                        addStatement("%S -> TODO()", item)
                    }
            }
            .addStatement("else -> null")
            .endControlFlow()
            .build()
    }
}
