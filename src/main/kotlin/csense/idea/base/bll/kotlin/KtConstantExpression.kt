@file:Suppress("unused")

package csense.idea.base.bll.kotlin

import org.jetbrains.kotlin.idea.caches.resolve.*
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.resolve.lazy.*
import org.jetbrains.kotlin.types.*
import org.jetbrains.kotlin.types.typeUtil.*


fun KtConstantExpression.isNumberType(): Boolean =
    resolveExpressionType()?.isPrimitiveNumberType() == true

