@file:Suppress("unused")

package csense.idea.base.bll.kotlin

import csense.idea.base.bll.psiWrapper.`class`.*
import csense.idea.base.bll.psiWrapper.`class`.operations.*
import org.jetbrains.kotlin.analysis.api.*
import org.jetbrains.kotlin.analysis.api.types.*
import org.jetbrains.kotlin.psi.*

fun KtThrowExpression.resolveThrownTypeOrNull(): KtPsiClass? {

    return tryResolveViaExpressionType()
        ?: this.thrownExpression?.resolveFirstClassType2()
        ?: KtPsiClass.getKotlinThrowable(project)
        ?: KtPsiClass.getJavaThrowable(project)

}

fun KtThrowExpression.tryResolveViaExpressionType(): KtPsiClass? {
    val expression = thrownExpression ?: return null
    val fqName: String = analyze(expression){
        expression.expressionType?.symbol?.classId?.asString()
    } ?: return null
    return KtPsiClass.resolveByKotlin(fqName = fqName, project = project)
        ?: KtPsiClass.resolveByJava(fqName = fqName, project = project)
}