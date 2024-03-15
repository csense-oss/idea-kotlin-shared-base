@file:Suppress("unused")

package csense.idea.base.bll.psiWrapper.`class`.operations.`is`

import csense.idea.base.bll.psiWrapper.`class`.*
import csense.idea.base.bll.psiWrapper.`class`.operations.*


fun KtPsiClass.isSubTypeOf(baseClass: KtPsiClass): Boolean {
    if (this.fqName == baseClass.fqName) {
        return true
    }
    forEachSuperClassType {
        if (it.fqName == baseClass.fqName) {
            return@isSubTypeOf true
        }
    }
    return false
}

fun KtPsiClass.isSubTypeOf(fqName: String): Boolean {
    if (this.fqName == fqName) {
        return true
    }
    forEachSuperClassType {
        if (it.fqName == fqName) {
            return@isSubTypeOf true
        }
    }
    return false
}
