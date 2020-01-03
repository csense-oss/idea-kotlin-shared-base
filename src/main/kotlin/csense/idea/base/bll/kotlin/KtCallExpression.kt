package csense.idea.base.bll.kotlin

import org.jetbrains.kotlin.psi.KtCallExpression
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
