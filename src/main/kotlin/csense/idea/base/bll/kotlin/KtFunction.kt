@file:Suppress("unused")

package csense.idea.base.bll.kotlin

import org.jetbrains.kotlin.lexer.KtTokens
import org.jetbrains.kotlin.psi.KtFunction

/**
 * Tells if this function have the inline keyword in the declaration
 * @receiver KtFunction
 * @return Boolean
 */
fun KtFunction.isInline(): Boolean {
    return modifierList?.getModifier(KtTokens.INLINE_KEYWORD) != null
}

fun KtFunction.isInlineWithInlineParameters(): Boolean {
    if (!isInline()) {
        return false
    }
    return valueParameters.any {
        it.isFunctionalAndNotNoInline()
    }
}