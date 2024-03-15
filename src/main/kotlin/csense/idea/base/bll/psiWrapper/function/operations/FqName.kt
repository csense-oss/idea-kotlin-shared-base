package csense.idea.base.bll.psiWrapper.function.operations

import csense.idea.base.bll.psiWrapper.function.*


val KtPsiFunction.fqName: String?
    get() = when (this) {
        is KtPsiFunction.Psi -> function.name
        is KtPsiFunction.Kt -> function.fqName?.asString()
    }