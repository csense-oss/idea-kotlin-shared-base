package csense.idea.base.bll.kotlin

import csense.idea.base.bll.uast.isChildOfSafe
import org.jetbrains.kotlin.descriptors.ValueDescriptor
import org.jetbrains.kotlin.idea.caches.resolve.resolveToDescriptorIfAny
import org.jetbrains.kotlin.js.resolve.diagnostics.findPsi
import org.jetbrains.kotlin.lexer.KtTokens
import org.jetbrains.kotlin.psi.KtFunctionType
import org.jetbrains.kotlin.psi.KtNameReferenceExpression
import org.jetbrains.kotlin.psi.KtParameter
import org.jetbrains.kotlin.psi.psiUtil.anyDescendantOfType
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


