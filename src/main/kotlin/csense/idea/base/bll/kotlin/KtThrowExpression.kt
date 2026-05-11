@file:Suppress("unused")

package csense.idea.base.bll.kotlin

import csense.idea.base.analysis.*
import csense.idea.base.bll.psiWrapper.`class`.*
import csense.idea.base.bll.psiWrapper.`class`.operations.*
import org.jetbrains.kotlin.psi.*

fun KtThrowExpression.resolveThrownTypeOrNull(): KtPsiClass? {

    return tryResolveViaExpressionType()
        ?: this.thrownExpression?.resolveFirstClassType2()
        ?: KtPsiClass.getKotlinThrowable(project)
        ?: KtPsiClass.getJavaThrowable(project)

}

fun KtThrowExpression.tryResolveViaExpressionType(): KtPsiClass? {
    val expression: KtExpression = thrownExpression ?: return null
    val fqName: String = expression.analyzeTypeName() ?: return null
    return KtPsiClass.resolveByKotlin(fqName = fqName, project = project)
        ?: KtPsiClass.resolveByJava(fqName = fqName, project = project)
}