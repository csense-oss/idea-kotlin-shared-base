@file:Suppress("unused", "INVISIBLE_MEMBER", "INVISIBLE_REFERENCE")

package csense.idea.base.bll.psiWrapper.function.operations

import com.intellij.psi.*
import csense.idea.base.bll.psiWrapper.function.*
import org.jetbrains.kotlin.psi.*

fun PsiElement.toKtPsiFunction(): KtPsiFunction? = when (this) {
    is KtFunction -> toKtPsiFunction()
    is PsiMethod -> toKtPsiFunction()
    else -> null
}

fun KtFunction.toKtPsiFunction(): KtPsiFunction.Kt =
    KtPsiFunction.Kt(this)
fun PsiMethod.toKtPsiFunction(): KtPsiFunction.Psi =
    KtPsiFunction.Psi(this)