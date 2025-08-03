package csense.idea.base.bll.kotlin

import org.jetbrains.kotlin.psi.*

fun KtTypeElement.isFunctional(): Boolean = this is KtFunctionType