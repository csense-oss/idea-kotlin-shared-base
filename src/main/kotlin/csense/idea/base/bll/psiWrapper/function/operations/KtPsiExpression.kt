package csense.idea.base.bll.psiWrapper.function.operations

import com.intellij.psi.*
import org.jetbrains.kotlin.psi.*

sealed interface KtPsiExpression {
    class Kt(val expression: KtExpression) : KtPsiExpression
    class Psi(val expression: PsiExpression) : KtPsiExpression
}


fun KtExpression.toKtPsiExpression(): KtPsiExpression.Kt {
    return KtPsiExpression.Kt(this)
}

fun PsiExpression.toKtPsiExpression(): KtPsiExpression.Psi {
    return KtPsiExpression.Psi(this)
}