package csense.idea.base.bll.kotlin

import org.jetbrains.kotlin.idea.quickfix.createFromUsage.callableBuilder.TypeInfo
import org.jetbrains.kotlin.idea.quickfix.createFromUsage.callableBuilder.getParameterInfos
import org.jetbrains.kotlin.psi.KtCallExpression
import org.jetbrains.kotlin.psi.KtLambdaExpression

fun KtLambdaExpression.resolveParameterIndex(): Int? {
    val callExp =
        parent?.parent as? KtCallExpression
            ?: parent?.parent?.parent as? KtCallExpression
            ?: return null
    return callExp.getParameterInfos().indexOfFirst {
        (it.typeInfo as? TypeInfo.ByExpression)?.expression === this
    }
}
