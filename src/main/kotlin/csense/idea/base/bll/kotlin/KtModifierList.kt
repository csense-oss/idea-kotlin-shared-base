package csense.idea.base.bll.kotlin

import org.jetbrains.kotlin.lexer.*
import org.jetbrains.kotlin.psi.*

fun KtModifierList.isOverriding(): Boolean = hasModifier(KtTokens.OVERRIDE_KEYWORD)