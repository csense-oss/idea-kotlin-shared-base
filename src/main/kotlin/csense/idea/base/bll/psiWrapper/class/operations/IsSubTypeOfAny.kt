package csense.idea.base.bll.psiWrapper.`class`.operations

import csense.idea.base.bll.psiWrapper.`class`.*
import csense.kotlin.extensions.*

fun KtPsiClass.isSubTypeOfAny(other: List<KtPsiClass>): Boolean {
    val fqNames: Set<String?> = other.mapToSet { it.fqName }

    if (fqNames.isEmpty()) {
        return false
    }

    if (this.fqName in fqNames) {
        return true
    }

    forEachSuperClassType { superClass: KtPsiClass ->
        if (superClass.fqName in fqNames) {
            return@isSubTypeOfAny true
        }
    }
    return false
}