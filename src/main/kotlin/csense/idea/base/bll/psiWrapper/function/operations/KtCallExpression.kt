@file:Suppress("unused")

package csense.idea.base.bll.psiWrapper.function.operations

import csense.idea.base.bll.kotlin.*
import csense.idea.base.bll.psiWrapper.function.*
import org.jetbrains.kotlin.psi.*

fun KtCallExpression.resolveMainReferenceAsFunction(): KtPsiFunction? {
    return resolveMainReference()?.toKtPsiFunction()
}
