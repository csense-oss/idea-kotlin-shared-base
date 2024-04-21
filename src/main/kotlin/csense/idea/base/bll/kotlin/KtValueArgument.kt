package csense.idea.base.bll.kotlin

import org.jetbrains.kotlin.psi.KtConstantExpression
import org.jetbrains.kotlin.psi.KtValueArgument

fun KtValueArgument.getFloatValueFromExpression(): Float? {
    return asConstantExpression()?.text?.toFloatOrNull()
}

fun KtValueArgument.getLongValueFromExpression(): Long? {
    return asConstantExpression()?.text?.toLongOrNull()
}

fun KtValueArgument.getIntValueFromExpression(): Int? {
    return asConstantExpression()?.text?.toIntOrNull()
}

fun KtValueArgument.asConstantExpression(): KtConstantExpression? = this.getArgumentExpression() as? KtConstantExpression