@file:Suppress("unused")

package csense.idea.base.bll.kotlin

import csense.idea.base.bll.ka.*
import org.jetbrains.kotlin.analysis.api.*
import org.jetbrains.kotlin.lexer.*
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.psi.psiUtil.*


fun KtParameter.isFunctionalAndNotNoInline(): Boolean {
    if (this.hasModifier(KtTokens.NOINLINE_KEYWORD)) {
        return false
    }
    return this.isFunctionalType()
}

fun KtParameter.isFunctionalType(): Boolean {
    return anyDescendantOfType<KtFunctionType>()
            || anyDescendantOfType<KtNameReferenceExpression> {
        it.isTypeAliasFunctional()
    }
}

fun KtParameter.isNumberTypeWithDefaultValue(): Boolean =
    isNumberType() && hasDefaultValue()

fun KtParameter.isNumberType(): Boolean {
    return analyze(this) {
        val expressionType = expressionType ?: return false
        isPrimitiveNumberOrNullableType(expressionType)
    }
}


fun KtParameter.allAnnotations(): List<KtAnnotationEntry> =
    annotationEntries + typeReference?.annotationEntries.orEmpty()