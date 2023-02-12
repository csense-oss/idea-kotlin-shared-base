@file:Suppress("unused")

package csense.idea.base.bll.kotlin

import csense.idea.base.bll.psiWrapper.`class`.*
import csense.idea.base.bll.psiWrapper.`class`.operations.*
import org.jetbrains.kotlin.psi.*

fun KtThrowExpression.resolveThrownTypeOrNull(): KtPsiClass? {
    return this.thrownExpression?.resolveFirstClassType2()
        ?: KtPsiClass.getKotlinThrowable(project)
        ?: KtPsiClass.getJavaThrowable(project)
}
