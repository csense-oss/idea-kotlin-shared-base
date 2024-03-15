@file:Suppress("unused")

package csense.idea.base.bll.kotlin

import com.intellij.psi.*
import csense.idea.base.bll.psi.*
import org.jetbrains.kotlin.idea.references.*
import org.jetbrains.kotlin.psi.*


/**
 * Find all argument names in order they are declared.
 * @receiver KtCallExpression
 * @return List<String?>
 */
fun KtCallExpression.findInvocationArgumentNamesNew(): List<String?> {
    return valueArguments.map { it: KtValueArgument? ->
        it?.getArgumentExpression()?.resolvePotentialArgumentName()
    }
}

/**
 * Used to try and find a potential name for a given argument.
 * @receiver KtExpression
 * @return String?
 */
tailrec fun KtExpression.resolvePotentialArgumentName(): String? = when (this) {
    is KtNameReferenceExpression -> getReferencedName()
    is KtDotQualifiedExpression -> {
        (selectorExpression ?: receiverExpression).resolvePotentialArgumentName()
    }

    is KtCallExpression -> {
        calleeExpression?.resolvePotentialArgumentName()
    }

    else -> null
}


/**
 * Resolves the original method.
 * @receiver KtCallExpression
 * @return PsiElement?
 */
fun KtCallExpression.resolveMainReference(): PsiElement? {
    return calleeExpression?.mainReference?.resolve()
}

fun KtCallExpression.resolveMainReferenceWithTypeAlias(): PsiElement? {
    return resolveMainReference()?.resolveTypeAliasOrThis()
}

/**
 * Resolves the original method.
 * @receiver KtCallExpression
 * @return PsiElement?
 */
fun KtCallExpression.resolveMainReferenceAsKtFunction(): KtFunction? {
    return resolveMainReference() as? KtFunction
}

/**
 * Resolves the original method.
 * @receiver KtCallExpression
 * @return PsiElement?
 */
fun KtCallExpression.resolveMainReferenceAsPsiMethod(): PsiMethod? {
    return resolveMainReference() as? PsiMethod
}

fun KtCallExpression.findArgumentNames(): List<String?> {
    return valueArguments.map { it: KtValueArgument? ->
        it?.getArgumentName()?.text
    }
}