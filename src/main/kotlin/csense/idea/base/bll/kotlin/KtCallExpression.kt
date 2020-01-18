package csense.idea.base.bll.kotlin

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiMethod
import org.jetbrains.kotlin.idea.references.mainReference
import org.jetbrains.kotlin.psi.KtCallExpression
import org.jetbrains.kotlin.psi.KtFunction
import org.jetbrains.kotlin.psi.KtNameReferenceExpression

/**
 * Find all argument names in order they are declared.
 * @receiver KtCallExpression
 * @return List<String?>
 */
fun KtCallExpression.findInvocationArgumentNames(): List<String?> {
    return valueArguments.map {
        val isNamed = it.getArgumentExpression() as? KtNameReferenceExpression
        isNamed?.getReferencedName()
    }
}

/**
 * Resolves the original method.
 * @receiver KtCallExpression
 * @return PsiElement?
 */
fun KtCallExpression.resolveMainReference(): PsiElement? {
    return calleeExpression?.mainReference?.resolve()
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
