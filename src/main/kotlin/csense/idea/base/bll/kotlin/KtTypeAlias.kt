package csense.idea.base.bll.kotlin

import org.jetbrains.kotlin.psi.KtTypeAlias

fun KtTypeAlias.isFunctional(): Boolean = getTypeReference()?.isFunctional() ?: false
