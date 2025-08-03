package csense.idea.base.bll.psiWrapper.`class`.operations

import com.intellij.openapi.project.*
import csense.idea.base.bll.psiWrapper.`class`.*

val KtPsiClass.project: Project
    get() = when (this) {
        is KtPsiClass.Psi -> psiClass.project
        is KtPsiClass.Kt -> ktClassOrObject.project
    }