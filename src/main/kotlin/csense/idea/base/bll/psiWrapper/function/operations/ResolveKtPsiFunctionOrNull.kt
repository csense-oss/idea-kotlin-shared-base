package csense.idea.base.bll.psiWrapper.function.operations

import com.intellij.psi.*
import csense.idea.base.bll.psiWrapper.function.*
import org.jetbrains.kotlin.psi.*


fun KtCallableReferenceExpression.resolveKtPsiFunctionOrNull(): KtPsiFunction? {
    return callableReference.references.firstNotNullOfOrNull { it: PsiReference? ->
        it?.resolve()?.toKtPsiFunction()
    }
}

//fun KtCallExpression.resolveKtPsiFunctionOrNull(): KtPsiFunction? {
//    return callableReference.references.firstNotNullOfOrNull { it: PsiReference? ->
//        it?.resolve()?.toKtPsiFunction()
//    }
//}