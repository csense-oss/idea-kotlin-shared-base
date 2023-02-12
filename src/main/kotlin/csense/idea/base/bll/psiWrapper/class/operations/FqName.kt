package csense.idea.base.bll.psiWrapper.`class`.operations

import csense.idea.base.bll.psiWrapper.`class`.*


val KtPsiClass.fqName: String?
    get() = when (this) {
        is KtPsiClass.Psi -> psiClass.qualifiedName
        is KtPsiClass.Kt ->  ktClassOrObject.fqName?.asString()
    }