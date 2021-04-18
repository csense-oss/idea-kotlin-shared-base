package csense.idea.base.bll.kotlin

import org.jetbrains.kotlin.lexer.KtTokens
import org.jetbrains.kotlin.psi.KtBinaryExpression

inline val KtBinaryExpression.isEqual: Boolean
    get() = this.operationToken == KtTokens.EQ

inline val KtBinaryExpression.isNotEqual: Boolean
    get() = !isEqual