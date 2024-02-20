@file:Suppress("unused")

package csense.idea.base.bll.psiWrapper.`class`.operations.filter

import csense.idea.base.bll.psiWrapper.`class`.*
import csense.idea.base.bll.psiWrapper.`class`.operations.`is`.*

//In short: in kotlin you cannot capture java.lang.Throwable
//and for some reason as the only type, kotlin.Throwable IS NOT a typealias.
//thus its actually unrelated type-wise, but behind the scenes (in the compiler) they are (the same?)
//so if there is any kotlin.throwable on the other side all exceptions are "related".
fun List<KtPsiClass>.filterUnrelatedExceptions(to: List<KtPsiClass>): List<KtPsiClass> {
    val anyRootKotlinThrowable: Boolean = to.any { it.isKotlinThrowable() }
    if (anyRootKotlinThrowable) {
        return emptyList()
    }
    return filterNonRelated(to)
}

