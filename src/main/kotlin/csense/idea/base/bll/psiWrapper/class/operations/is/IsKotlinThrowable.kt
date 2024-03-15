@file:Suppress("unused")

package csense.idea.base.bll.psiWrapper.`class`.operations.`is`

import csense.idea.base.bll.psiWrapper.`class`.*
import csense.idea.base.bll.psiWrapper.`class`.operations.*

fun KtPsiClass.isKotlinThrowable(): Boolean = fqName == "kotlin.Throwable"