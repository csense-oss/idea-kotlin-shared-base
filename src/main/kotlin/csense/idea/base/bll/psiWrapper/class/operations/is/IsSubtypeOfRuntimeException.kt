@file:Suppress("unused")

package csense.idea.base.bll.psiWrapper.`class`.operations.`is`

import csense.idea.base.bll.psiWrapper.`class`.*
import csense.idea.base.bll.psiWrapper.`class`.operations.*

fun KtPsiClass.isSubtypeOfRuntimeException(): Boolean =
    isSubtypeOfKotlinRuntimeException() || isSubtypeOfJavaRuntimeException()


fun KtPsiClass.isSubtypeOfJavaRuntimeException(): Boolean =
    isSubTypeOf(KtPsiClass.javaRuntimeExceptionFqName)

fun KtPsiClass.isSubtypeOfKotlinRuntimeException(): Boolean =
    isSubTypeOf(KtPsiClass.kotlinRuntimeExceptionFqName)

fun List<KtPsiClass>.filterNotSubTypeOfRuntimeException(): List<KtPsiClass> = filterNot { it: KtPsiClass ->
    it.isSubtypeOfRuntimeException()
}
