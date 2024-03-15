@file:Suppress("unused")

package csense.idea.base.bll.ast

import com.intellij.psi.tree.*
import org.jetbrains.kotlin.lexer.*


fun IElementType.isKtIdentifier(): Boolean =
    this == KtTokens.IDENTIFIER

fun IElementType.isNotKtIdentifier(): Boolean =
    !isKtIdentifier()