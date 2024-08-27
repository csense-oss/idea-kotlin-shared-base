package csense.idea.base.bll.psiWrapper.`class`.operations

import com.intellij.psi.*
import csense.idea.base.bll.psiWrapper.`class`.*
import org.jetbrains.kotlin.psi.*

fun PsiElement.asKtOrPsiClass(): KtPsiClass? = when (this) {
    is KtClassOrObject -> asKtOrPsiClass()
    is PsiClass -> asKtOrPsiClass()
    else -> null
}

fun PsiClass.asKtOrPsiClass(): KtPsiClass.Psi =
    KtPsiClass.Psi(this)

fun KtClassOrObject.asKtOrPsiClass(): KtPsiClass.Kt =
    KtPsiClass.Kt(this)


fun KtPsiClass.psiElement(): PsiElement = when (this) {
    is KtPsiClass.Kt -> ktClassOrObject
    is KtPsiClass.Psi -> psiClass
}