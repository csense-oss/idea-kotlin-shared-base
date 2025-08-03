package csense.idea.base.bll.kotlin

import com.intellij.psi.*
import csense.idea.base.bll.ka.*
import csense.idea.base.bll.kotlin.models.*
import org.jetbrains.kotlin.analysis.api.*
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.psi.psiUtil.*

/**
 * Simple heuristic / not fully done.
 * @receiver KtExpression
 * @return Boolean
 */
fun KtExpression.isConstant(): Boolean = when (this) {
    is KtNameReferenceExpression -> {
        val asProp: KtProperty? = resolveFirstClassType() as? KtProperty
        asProp?.isConstantProperty() ?: false
    }

    is KtConstantExpression -> true
    is KtStringTemplateExpression -> this.isConstant()
    is KtBlockExpression -> (children.singleOrNull() as? KtExpression)?.isConstant() == true
    is KtReturnExpression -> (children.singleOrNull() as? KtExpression)?.isConstant() == true
    is KtBinaryExpression -> (left?.isConstant() ?: false) && (right?.isConstant() ?: false)
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
    return analyze(this){
        fqClassNameAsString(expressionType)
    }
}

fun KtExpression.isTypeReference(): Boolean {
    return parent is KtUserType
}

fun KtExpression.resolveAsReferenceToPropertyOrValueParameter(): KtParameterOrValueParameter? {
    val asReference: KtNameReferenceExpression = this as? KtNameReferenceExpression ?: return null
    return asReference.references.firstNotNullOfOrNull { it: PsiReference ->
        when (val resolved: PsiElement? = it.resolve()) {
            is KtParameter -> KtParameterOrValueParameter.ValueParameter(resolved)
            is KtProperty -> KtParameterOrValueParameter.Property(resolved)
            else -> null
        }
    }
}


fun KtExpression.tryResolveToCallExpression(): PsiMethod? = when (this) {
    is KtSuperExpression -> (parent as? KtExpression)?.tryResolveToCallExpression()
    is KtDotQualifiedExpression -> selectorExpression?.tryResolveToCallExpression()
    is KtCallExpression -> resolveMainReferenceAsPsiMethod()
    else -> null
}