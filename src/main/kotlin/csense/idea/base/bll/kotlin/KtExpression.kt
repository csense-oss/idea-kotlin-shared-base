package csense.idea.base.bll.kotlin

import com.intellij.psi.*
import csense.idea.base.bll.kotlin.models.*
import org.jetbrains.kotlin.idea.caches.resolve.analyze
import org.jetbrains.kotlin.js.descriptorUtils.nameIfStandardType

import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.psi.psiUtil.isPlainWithEscapes
import org.jetbrains.kotlin.resolve.*
import org.jetbrains.kotlin.resolve.lazy.*
import org.jetbrains.kotlin.types.*

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
    val type: KotlinType? = resolveExpressionType()
    return type?.nameIfStandardType?.asString()
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


/**
 * Only usable on "expressions" not say [KtDeclaration]s (where you should use [KtDeclaration.resolveType] instead)
 */
fun KtExpression.resolveExpressionType(
    context: BindingContext = this.analyze(BodyResolveMode.PARTIAL),
): KotlinType? = context.getType(this)


fun KtExpression.tryResolveToCallExpression(): PsiMethod? = when (this) {
    is KtSuperExpression -> (parent as? KtExpression)?.tryResolveToCallExpression()
    is KtDotQualifiedExpression -> selectorExpression?.tryResolveToCallExpression()
    is KtCallExpression -> resolveMainReferenceAsPsiMethod()
    else -> null
}