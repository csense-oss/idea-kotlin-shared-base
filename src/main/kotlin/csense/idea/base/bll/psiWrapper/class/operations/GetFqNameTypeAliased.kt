package csense.idea.base.bll.psiWrapper.`class`.operations

import csense.idea.base.bll.kotlin.*
import csense.idea.base.bll.psiWrapper.`class`.*

fun KtPsiClass.getFqNameTypeAliased(): String? {
    return typeAlias?.resolveRealFqName()
}