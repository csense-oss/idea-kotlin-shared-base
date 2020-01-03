package csense.idea.base.bll.kotlin

import org.jetbrains.kotlin.lexer.KtTokens
import org.jetbrains.kotlin.psi.KtModifierList

fun KtModifierList.isOverriding(): Boolean = hasModifier(KtTokens.OVERRIDE_KEYWORD)