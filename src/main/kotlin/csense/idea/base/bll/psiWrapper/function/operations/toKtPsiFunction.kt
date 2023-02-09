@file:Suppress("unused")

package csense.idea.base.bll.psiWrapper.function.operations

import com.intellij.psi.*
import csense.idea.base.bll.psiWrapper.function.*
import org.jetbrains.kotlin.psi.*

fun PsiElement.toKtPsiFunction(): KtPsiFunction? = when (this) {
    is KtFunction -> KtPsiFunction.Kt(this)
    is PsiMethod -> KtPsiFunction.Psi(this)
    else -> null
}