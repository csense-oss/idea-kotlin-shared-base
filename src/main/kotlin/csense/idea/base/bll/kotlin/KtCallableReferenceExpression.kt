package csense.idea.base.bll.kotlin

import com.intellij.psi.*
import org.jetbrains.kotlin.psi.*

fun KtCallableReferenceExpression.resolveFunctionOrNull(): KtNamedFunction? {
    return callableReference.references.firstNotNullOfOrNull { it: PsiReference? ->
        it?.resolve() as? KtNamedFunction
    }
}