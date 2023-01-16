@file:Suppress("unused")

package csense.idea.base.bll.kotlin

import org.jetbrains.kotlin.lexer.KtTokens
import org.jetbrains.kotlin.psi.KtBinaryExpression

//TODO THIS IS SEMANTICALLY WRONG: ITS NOT "EQUAL" ( == ) but Assignment (=)....

inline val KtBinaryExpression.isEqual: Boolean
    get() = this.operationToken == KtTokens.EQ

inline val KtBinaryExpression.isNotEqual: Boolean
    get() = !isEqual

inline val KtBinaryExpression.isAssignment: Boolean
    get() = this.operationToken == KtTokens.EQ

inline val KtBinaryExpression.isNotAssignment: Boolean
    get() = !isAssignment