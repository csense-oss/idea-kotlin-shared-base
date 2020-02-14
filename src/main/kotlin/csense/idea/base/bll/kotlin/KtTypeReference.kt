package csense.idea.base.bll.kotlin

import com.intellij.psi.PsiElement
import csense.idea.base.UastKtPsi.resolvePsi
import org.jetbrains.kotlin.psi.KtTypeReference
import org.jetbrains.kotlin.psi.KtUserType

fun KtTypeReference.isFunctional(): Boolean = typeElement?.isFunctional() ?: false

fun KtTypeReference.resolve(): PsiElement? = (typeElement as? KtUserType)
    ?.referenceExpression
    ?.resolvePsi()