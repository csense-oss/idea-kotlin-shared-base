@file:Suppress("unused")

package csense.idea.base.bll.psiWrapper.`class`.operations.`is`

import csense.idea.base.bll.psiWrapper.`class`.*
import csense.idea.base.bll.psiWrapper.`class`.operations.*
import csense.idea.base.bll.psiWrapper.`class`.operations.any.*
import csense.idea.base.csense.*
import csense.kotlin.extensions.*
import kotlin.collections.filterNotNull

fun KtPsiClass.isSubTypeOfAny(other: List<KtPsiClass>): Boolean {
    val allFqName: Set<String?> = other.allFqNames()
    if (allFqName.isEmpty()) {
        return false
    }
    if (this.fqName in allFqName) {
        return true
    }
    return anySuperClassType { superClass: KtPsiClass ->
        superClass.fqName in allFqName
    }
}