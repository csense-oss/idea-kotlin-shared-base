@file:Suppress("unused")

package csense.idea.base.bll.psiWrapper.`class`.operations.`is`

import csense.idea.base.bll.psiWrapper.`class`.*
import csense.idea.base.bll.psiWrapper.`class`.operations.*
import csense.idea.base.bll.psiWrapper.`class`.operations.any.*

fun KtPsiClass.isSubTypeOfAny(other: List<KtPsiClass>): Boolean {
    val allFqName: Set<String?> = other.allFqNames()
    if (allFqName.isEmpty()) {
        return false
    }
    if (this.fqName in allFqName) {
        return true
    }

    val alias: String? = this.typeAlias?.getTypeReference()?.resolveFirstClassType2()?.fqName
    if (alias in allFqName) {
        return true
    }

    return anySuperClassType { superClass: KtPsiClass ->
        superClass.fqName in allFqName || superClass.typeAlias?.getTypeReference()?.resolveFirstClassType2()?.fqName in allFqName
    }
}