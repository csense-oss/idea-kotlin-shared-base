package csense.idea.base.bll.kotlin

import org.jetbrains.kotlin.psi.KtTypeReference

fun KtTypeReference.isFunctional(): Boolean = typeElement?.isFunctional() ?: false