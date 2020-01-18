package csense.idea.base.bll.kotlin

import org.jetbrains.kotlin.psi.KtConstantExpression
import org.jetbrains.kotlin.psi.KtValueArgument

fun KtValueArgument.getFloatValueFromExpression(): Float? {
    val exp = this.getArgumentExpression() as? KtConstantExpression ?: return null
    return exp.text.toFloatOrNull()
}

fun KtValueArgument.getLongValueFromExpression(): Long? {
    val exp = this.getArgumentExpression() as? KtConstantExpression ?: return null
    return exp.text.toLongOrNull()
}

fun KtValueArgument.getIntValueFromExpression(): Int? {
    val exp = this.getArgumentExpression() as? KtConstantExpression ?: return null
    return exp.text.toIntOrNull()
}
