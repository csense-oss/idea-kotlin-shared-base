@file:Suppress("unused")

package csense.idea.base.bll.kotlin

import csense.idea.base.bll.uast.isChildOfSafe
import org.jetbrains.kotlin.lexer.KtTokens
import org.jetbrains.kotlin.nj2k.postProcessing.*
import org.jetbrains.kotlin.psi.KtAnnotationEntry
import org.jetbrains.kotlin.psi.KtFunctionType
import org.jetbrains.kotlin.psi.KtNameReferenceExpression
import org.jetbrains.kotlin.psi.KtParameter
import org.jetbrains.kotlin.psi.psiUtil.anyDescendantOfType
import org.jetbrains.kotlin.types.typeUtil.*
import org.jetbrains.uast.UClass
import org.jetbrains.uast.toUElement

fun KtParameter.resolveTypeClass(): UClass? {
    return this.typeReference?.resolve()?.toUElement(UClass::class.java)
}

fun KtParameter.isSubtypeOf(otherType: UClass): Boolean {
    val caughtClass = resolveTypeClass()
        ?: return false
    return otherType.isChildOfSafe(caughtClass)
}


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

fun KtParameter.isNumberType(): Boolean =
    resolveType()?.isPrimitiveNumberOrNullableType() == true

fun KtParameter.allAnnotations(): List<KtAnnotationEntry> =
    annotationEntries + typeReference?.annotationEntries.orEmpty()
