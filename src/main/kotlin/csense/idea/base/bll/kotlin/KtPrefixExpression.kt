@file:Suppress("unused")

package csense.idea.base.bll.kotlin

import com.intellij.openapi.util.*
import org.jetbrains.kotlin.psi.*

fun KtPrefixExpression.textRangeOfOperator(): TextRange =
    operationReference.textRange