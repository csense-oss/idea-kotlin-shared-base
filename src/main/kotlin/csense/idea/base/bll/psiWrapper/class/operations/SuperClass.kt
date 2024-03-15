package csense.idea.base.bll.psiWrapper.`class`.operations

import csense.idea.base.bll.psiWrapper.`class`.*
import csense.idea.base.bll.psiWrapper.`class`.operations.to.*
import org.jetbrains.kotlin.psi.*



fun KtPsiClass.superClass(): KtPsiClass? {
    return when (this) {
        is KtPsiClass.Kt -> superClass()
        is KtPsiClass.Psi -> superClass()
    }
}

fun KtPsiClass.Kt.superClass(): KtPsiClass? {
    val superTypes: List<KtSuperTypeListEntry> = ktClassOrObject.superTypeListEntries
    return superTypes.firstNotNullOfOrNull { it: KtSuperTypeListEntry ->
        it.resolveFirstClassType2()
    }
}

fun KtPsiClass.Psi.superClass(): KtPsiClass? {
    return psiClass.superClass?.toKtPsiClass()
}