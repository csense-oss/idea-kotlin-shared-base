package csense.idea.base.UastKtPsi

import org.jetbrains.kotlin.idea.debugger.sequence.psi.resolveType
import org.jetbrains.kotlin.psi.KtConstantExpression
import org.jetbrains.kotlin.types.typeUtil.isPrimitiveNumberType


fun KtConstantExpression.isNumberType(): Boolean {
    return this.resolveType().isPrimitiveNumberType()
}