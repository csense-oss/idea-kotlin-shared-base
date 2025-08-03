package csense.idea.base.bll.psiWrapper.function.operations

import com.intellij.openapi.project.*
import csense.idea.base.bll.psiWrapper.function.*

val KtPsiFunction.project: Project
    get() = when (this) {
        is KtPsiFunction.Psi -> function.project
        is KtPsiFunction.Kt -> function.project
    }