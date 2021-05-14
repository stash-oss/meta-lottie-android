package com.stash.detekt.rules

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Debt.Companion.FIVE_MINS
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import org.jetbrains.kotlin.com.intellij.psi.PsiElement
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType
import org.jetbrains.kotlin.lexer.KtTokens.LBRACE
import org.jetbrains.kotlin.lexer.KtTokens.WHITE_SPACE
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.psiUtil.siblings
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes.ENUM_ENTRY
import io.gitlab.arturbosch.detekt.api.Severity.CodeSmell as CodeSmellSeverity

/**
 * This rule reporting companion object position inside a class.
 * more details on extending detekt can be found here https://arturbosch.github.io/detekt/extensions.html
 */
class CompanionObjectPosition : Rule() {

    override val issue = Issue(
        javaClass.simpleName,
        CodeSmellSeverity,
        "This rule reports a companion object position in a class.",
        FIVE_MINS
    )

    private val classBeforeSiblingOrder = listOf<IElementType>(WHITE_SPACE, LBRACE)
    private val enumBeforeSiblingOrder = listOf<IElementType>(WHITE_SPACE, ENUM_ENTRY)

    override fun visitClass(klass: KtClass) {
        super.visitClass(klass)

        klass.companionObjects.firstOrNull()?.let {

            val beforeSiblings = it.siblings(forward = false, withItself = false)

            val matches = if (klass.isEnum()) {
                isPositionWithinEnumValid(beforeSiblings)
            } else {
                isPositionWithinClassValid(beforeSiblings)
            }

            if (!matches) {
                report(
                    CodeSmell(
                        issue, Entity.from(it),
                        message = "Companion object declaration must be at the top of the class ${klass.name}"
                    )
                )
            }
        }
    }

    private fun isPositionWithinClassValid(beforeSiblings: Sequence<PsiElement>): Boolean {
        val siblingsToMatch = beforeSiblings
            .take(classBeforeSiblingOrder.size)
            .map { sibling -> sibling.node.elementType }
            .toList()
        return classBeforeSiblingOrder == siblingsToMatch
    }

    private fun isPositionWithinEnumValid(beforeSiblings: Sequence<PsiElement>): Boolean {
        val siblingsToMatch = beforeSiblings
            .take(enumBeforeSiblingOrder.size)
            .map { sibling -> sibling.node.elementType }
            .toList()
        return enumBeforeSiblingOrder == siblingsToMatch
    }
}
