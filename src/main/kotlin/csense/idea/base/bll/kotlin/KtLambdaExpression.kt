@file:Suppress("unused")

package csense.idea.base.bll.kotlin

import csense.idea.base.bll.psiWrapper.function.*
import csense.idea.base.bll.psiWrapper.function.operations.*
import org.jetbrains.kotlin.idea.quickfix.createFromUsage.callableBuilder.TypeInfo
import org.jetbrains.kotlin.idea.quickfix.createFromUsage.callableBuilder.getParameterInfos
import org.jetbrains.kotlin.psi.*

fun KtLambdaExpression.resolveParameterIndex(): Int? {
    val callExp: KtCallExpression = resolveParentCallExpression() ?: return null
    return callExp.getParameterInfos().indexOfFirst {
        (it.typeInfo as? TypeInfo.ByExpression)?.expression === this
    }
}

fun KtLambdaExpression.resolveParameterFunction(): KtPsiFunction? {
    return resolveParentCallExpression()?.resolveMainReferenceAsFunction()
}

fun KtLambdaExpression.resolveParentCallExpression(): KtCallExpression? {
    return parent?.parent as? KtCallExpression
        ?: parent?.parent?.parent as? KtCallExpression
}