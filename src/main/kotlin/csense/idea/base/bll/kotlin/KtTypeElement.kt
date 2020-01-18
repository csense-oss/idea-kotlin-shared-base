package csense.idea.base.bll.kotlin

import org.jetbrains.kotlin.psi.KtFunctionType
import org.jetbrains.kotlin.psi.KtTypeElement

fun KtTypeElement.isFunctional(): Boolean = this is KtFunctionType