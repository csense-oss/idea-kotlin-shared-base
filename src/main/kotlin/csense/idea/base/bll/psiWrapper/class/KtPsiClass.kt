package csense.idea.base.bll.psiWrapper.`class`

import com.intellij.psi.*
import org.jetbrains.kotlin.psi.*

sealed interface KtPsiClass {
    val typeAlias: KtTypeAlias?

    data class Psi(
        val psiClass: PsiClass,
        override val typeAlias: KtTypeAlias? = null
    ) : KtPsiClass

    data class Kt(
        val ktClassOrObject: KtClassOrObject,
        override val typeAlias: KtTypeAlias? = null
    ) : KtPsiClass

    companion object
}
