package csense.idea.base.bll.kotlin

import org.jetbrains.kotlin.idea.caches.resolve.analyze
import org.jetbrains.kotlin.js.descriptorUtils.nameIfStandardType
import org.jetbrains.kotlin.nj2k.postProcessing.resolve
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.psi.psiUtil.isPlainWithEscapes

/**
 * Simple heuristic / not fully done.
 * @receiver KtExpression
 * @return Boolean
 */
fun KtExpression.isConstant(): Boolean = when (this) {
    is KtNameReferenceExpression -> {
        val asProp = resolve() as? KtProperty
        asProp?.isGetterConstant() ?: false
    }
    is KtConstantExpression -> {
        true
    }
    is KtStringTemplateExpression -> {
        this.isConstant()
    }
    is KtBlockExpression -> {
        children.size == 1 && (children[0] as? KtExpression)?.isConstant() == true
    }
    is KtReturnExpression -> {
        children.size == 1 && (children[0] as? KtExpression)?.isConstant() == true
    }
    is KtBinaryExpression -> {
        (left?.isConstant() ?: false) && (right?.isConstant() ?: false)
    }
    else -> false
}

fun KtStringTemplateExpression.isConstant(): Boolean =
    isPlainWithEscapes()

/**
 * Tries to analyze what type this expression resolves to and then get it as a string
 * @receiver KtExpression
 * @return String?
 */
fun KtExpression.computeTypeAsString(): String? {
    val type = analyze().getType(this)
    return type?.nameIfStandardType?.asString()
}

fun KtExpression.isTypeReference(): Boolean {
    return parent is KtUserType
}