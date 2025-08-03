@file:Suppress("unused")

package csense.idea.base.bll.kotlin

import org.jetbrains.kotlin.analysis.api.*
import org.jetbrains.kotlin.analysis.api.types.*
import org.jetbrains.kotlin.psi.*


fun KtConstantExpression.isNumberType(): Boolean {
    return analyze(this) {
        val expressionType: KaType = expressionType ?: return false
        expressionType.isPrimitive && !expressionType.isBooleanType
    }
}