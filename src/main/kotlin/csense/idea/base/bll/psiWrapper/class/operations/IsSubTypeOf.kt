package csense.idea.base.bll.psiWrapper.`class`.operations

import csense.idea.base.bll.psiWrapper.`class`.*


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


//TODO move and refactor to have shared names
fun KtPsiClass.isSubtypeOfRuntimeException(): Boolean =
    isSubTypeOf("java.lang.RuntimeException") ||
            isSubTypeOf("kotlin.RuntimeException")

