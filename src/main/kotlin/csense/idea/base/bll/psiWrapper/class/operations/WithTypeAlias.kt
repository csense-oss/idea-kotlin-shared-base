package csense.idea.base.bll.psiWrapper.`class`.operations

import csense.idea.base.bll.psiWrapper.`class`.*
import org.jetbrains.kotlin.psi.*

fun KtPsiClass.withTypeAlias(typeAlias: KtTypeAlias): KtPsiClass = when (this) {
    is KtPsiClass.Kt -> withTypeAlias(typeAlias)
    is KtPsiClass.Psi -> withTypeAlias(typeAlias)
}

fun KtPsiClass.Kt.withTypeAlias(typeAlias: KtTypeAlias): KtPsiClass.Kt = copy(typeAlias = typeAlias)

fun KtPsiClass.Psi.withTypeAlias(typeAlias: KtTypeAlias): KtPsiClass.Psi = copy(typeAlias = typeAlias)