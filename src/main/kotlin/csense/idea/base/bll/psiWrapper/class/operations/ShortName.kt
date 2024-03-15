package csense.idea.base.bll.psiWrapper.`class`.operations

import csense.idea.base.bll.psiWrapper.`class`.*


val KtPsiClass.shortName: String?
    get() = when (this) {
        is KtPsiClass.Psi -> psiClass.name
        is KtPsiClass.Kt -> ktClassOrObject.name
    }