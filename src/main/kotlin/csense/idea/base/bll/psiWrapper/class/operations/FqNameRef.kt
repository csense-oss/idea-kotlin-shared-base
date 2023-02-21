package csense.idea.base.bll.psiWrapper.`class`.operations

import csense.idea.base.bll.psiWrapper.`class`.*

fun KtPsiClass.fqNameRef(): String =
    "$fqName::class"