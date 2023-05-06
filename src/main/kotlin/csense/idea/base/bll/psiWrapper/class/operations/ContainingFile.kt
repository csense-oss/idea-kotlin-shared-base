package csense.idea.base.bll.psiWrapper.`class`.operations

import com.intellij.psi.*
import csense.idea.base.bll.psiWrapper.`class`.*

fun KtPsiClass.containingFile(): PsiFile = when (this) {
    is KtPsiClass.Kt -> ktClassOrObject.containingKtFile
    is KtPsiClass.Psi -> psiClass.containingFile
}