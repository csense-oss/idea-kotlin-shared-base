package csense.idea.base.bll.psiWrapper.`class`.operations

import csense.idea.base.bll.psiWrapper.`class`.*

fun KtPsiClass.getFqNameTypeAliased(): String? {
    //TODO is this not wrong, as fqname of typealias is the name of the kotlin class not the pointed to class...
    return typeAlias?.fqName?.asString() ?: fqName
}