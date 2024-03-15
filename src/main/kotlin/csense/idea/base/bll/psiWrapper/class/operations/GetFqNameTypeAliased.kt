package csense.idea.base.bll.psiWrapper.`class`.operations

import csense.idea.base.bll.psiWrapper.`class`.*

fun KtPsiClass.getFqNameTypeAliased(): String? {
    return typeAlias?.fqName?.asString() ?: fqName
}