package csense.idea.base.bll.psiWrapper.function.operations

import com.intellij.psi.*
import csense.idea.base.bll.psiWrapper.function.*

val KtPsiFunction.containingFile: PsiFile?
    get() = when (this) {
        is KtPsiFunction.Psi -> function.containingFile
        is KtPsiFunction.Kt -> function.containingFile
    }
