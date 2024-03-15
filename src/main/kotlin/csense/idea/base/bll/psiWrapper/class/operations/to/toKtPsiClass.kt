package csense.idea.base.bll.psiWrapper.`class`.operations.to

import com.intellij.psi.PsiClass
import csense.idea.base.bll.psiWrapper.`class`.*
import org.jetbrains.kotlin.psi.*

fun KtClassOrObject.toKtPsiClass(): KtPsiClass.Kt {
    return KtPsiClass.Kt(this)
}

fun PsiClass.toKtPsiClass(): KtPsiClass.Psi {
    return KtPsiClass.Psi(this)
}
