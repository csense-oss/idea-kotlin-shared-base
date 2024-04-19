@file:Suppress("unused")

package csense.idea.base.bll.kotlin

import csense.idea.base.bll.psiWrapper.`class`.*
import csense.idea.base.bll.psiWrapper.`class`.operations.*
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.types.*

fun KtThrowExpression.resolveThrownTypeOrNull(): KtPsiClass? {

    return tryResolveViaExpressionType()
        ?: this.thrownExpression?.resolveFirstClassType2()
        ?: KtPsiClass.getKotlinThrowable(project)
        ?: KtPsiClass.getJavaThrowable(project)

}

fun KtThrowExpression.tryResolveViaExpressionType(): KtPsiClass? {
    val resolvedExpressionType: KotlinType = thrownExpression?.resolveExpressionType() ?: return null
    val fqName: String = resolvedExpressionType.fqNameAsString() ?: return null
    return KtPsiClass.resolveByKotlin(fqName = fqName, project = project)
        ?: KtPsiClass.resolveByJava(fqName = fqName, project = project)
}