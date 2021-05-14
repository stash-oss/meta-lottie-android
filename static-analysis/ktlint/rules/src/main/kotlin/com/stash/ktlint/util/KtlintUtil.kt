package com.stash.ktlint.util

import org.jetbrains.kotlin.psi.KtAnnotated
import org.jetbrains.kotlin.psi.KtAnnotationEntry
import org.jetbrains.kotlin.psi.KtUserType
import org.jetbrains.kotlin.utils.addToStdlib.safeAs

private val suppressAnnotations = setOf("Suppress", "SuppressWarnings")

/**
 * Adds support for @Suppress annotations when lint.
 *
 * Kotlin lint uses weird syntax for suppressing violations:
 * https://github.com/pinterest/ktlint#how-do-i-suppress-an-error-for-a-lineblockfile
 */
fun KtAnnotated.isSuppressed(ruleId: String): Boolean {
    return annotationEntries
        .filter {
            it.calleeExpression?.constructorReferenceExpression
                ?.getReferencedName() in suppressAnnotations
        }.flatMap(KtAnnotationEntry::getValueArguments)
        .mapNotNull { it.getArgumentExpression()?.text?.removeSurrounding("\"") }
        .any { it == ruleId }
}

fun KtAnnotated.hasAnnotation(vararg annotationNames: String): Boolean {
    val names = annotationNames.toHashSet()
    return annotationEntries.any {
        it.typeReference
            ?.typeElement
            ?.safeAs<KtUserType>()
            ?.referencedName in names
    }
}
