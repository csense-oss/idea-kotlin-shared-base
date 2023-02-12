package csense.idea.base.bll.psiWrapper.`class`.operations

import com.intellij.openapi.project.Project
import csense.idea.base.bll.psiWrapper.`class`.*
import csense.idea.base.bll.psiWrapper.function.*

val KtPsiClass.project: Project
    get() = when (this) {
        is KtPsiClass.Psi -> psiClass.project
        is KtPsiClass.Kt -> ktClassOrObject.project
    }