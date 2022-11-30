package com.stash.metalottie.plugin.tasks.generator.writer

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.MemberName
import com.stash.metalottie.plugin.tasks.generator.model.LottieMetadata
import com.stash.metalottie.plugin.tasks.generator.model.LottieThemePath
import java.io.File
import java.util.Locale

class DynamicPropertiesWriter {
    companion object {
        private val SrcDirNamespaceRegex =
            "src${File.separator}main${File.separator}(?:java|kotlin)${File.separator}".toRegex()

        private val composableClassName = ClassName(
            "androidx.compose.runtime",
            "Composable"
        )
        private val lottieDynamicPropertiesClassName = ClassName(
            "com.airbnb.lottie.compose",
            "LottieDynamicProperties"
        )
        private val rememberLottieDynamicPropertiesClassName = ClassName(
            "com.airbnb.lottie.compose",
            "rememberLottieDynamicProperties"
        )
        private val rememberLottieDynamicPropertyClassName = ClassName(
            "com.airbnb.lottie.compose",
            "rememberLottieDynamicProperty"
        )
        private val lottiePropertyClassName = ClassName(
            "com.airbnb.lottie",
            "LottieProperty"
        )
        private val localMetaLottieMemberName = MemberName(
            "com.stash.metalottie.compose",
            "LocalMetaLottie"
        )
    }

    fun write(
        metadata: LottieMetadata,
        name: String,
        outDirectory: File
    ) {
        val startIndex = SrcDirNamespaceRegex.find(outDirectory.absolutePath)
            ?: throw Exception("Could not determine the namespace from source directory given: ${outDirectory.absolutePath}")
        val namespace = outDirectory.absolutePath
            .substring(startIndex.range.last + 1)
            .replace(File.separator, ".")
        val sourceDirectory = File(outDirectory.absolutePath.substring(0, startIndex.range.first))

        buildFileSpec(metadata, name, namespace).writeTo(sourceDirectory)
    }

    fun write(
        metadata: LottieMetadata,
        name: String,
        namespace: String,
        out: Appendable
    ) {
        buildFileSpec(metadata, name, namespace).writeTo(out)
    }

    private fun buildFileSpec(
        metadata: LottieMetadata,
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
            .builder(namespace, "${capitalizedName}DynamicProperties")
            .addFunction(
                FunSpec.builder("remember${capitalizedName}DynamicProperties")
                    .addAnnotation(composableClassName)
                    .returns(lottieDynamicPropertiesClassName)
                    .apply {
                        val blockBuilder = CodeBlock.builder()
                            .addStatement("// Generated dynamic properties for a single lottie file")
                            .add("return %T(", rememberLottieDynamicPropertiesClassName)

                        metadata.themePaths.forEach { themePath ->
                            blockBuilder.add(
                                generateDynamicProperty(themePath),
                                rememberLottieDynamicPropertyClassName,
                                lottiePropertyClassName,
                                localMetaLottieMemberName
                            )
                        }

                        blockBuilder.add("\n)")
                        addCode(blockBuilder.build())
                    }
                    .build()
            )
            .build()
    }

    private fun generateDynamicProperty(themePath: LottieThemePath): String {
        val propertyType = when (themePath.property) {
            "STROKE_COLOR" -> "%T.STROKE_COLOR"
            else -> "%T.COLOR"
        }
        val keyPath = themePath.keyPath.joinToString(separator = ",") { "\"$it\"" }
        val value =
            "%M.current.themeColorProvider.getValue(\"${themePath.themeKey}\")·?:·0x${themePath.color}"

        return """
            |
            |  %T(
            |    property·=·$propertyType,
            |    value·=·$value,
            |    keyPath·=·arrayOf($keyPath)
            |  ),
            """.trimMargin()
    }
}
