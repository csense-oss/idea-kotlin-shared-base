package csense.idea.base.bll.kotlin

import csense.idea.base.bll.psiWrapper.`class`.operations.*
import org.jetbrains.kotlin.psi.*

fun KtTypeAlias.isFunctional(): Boolean = getTypeReference()?.isFunctional() == true

fun KtTypeAlias.resolveRealFqName(): String?{
    return getTypeReference()?.resolveFirstClassType2()?.fqName
}