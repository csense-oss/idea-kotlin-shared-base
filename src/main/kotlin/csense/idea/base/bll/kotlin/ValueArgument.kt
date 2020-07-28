package csense.idea.base.bll.kotlin

import org.jetbrains.kotlin.psi.KtCallExpression
import org.jetbrains.kotlin.psi.KtClassLiteralExpression
import org.jetbrains.kotlin.psi.KtCollectionLiteralExpression
import org.jetbrains.kotlin.psi.ValueArgument

fun ValueArgument.resolveClassLiterals(): List<KtClassLiteralExpression> {
    return when (val argumentExpression = getArgumentExpression()) {
        is KtClassLiteralExpression -> listOf(argumentExpression)
        is KtCollectionLiteralExpression -> argumentExpression.getInnerExpressions().filterIsInstance(
            KtClassLiteralExpression::class.java
        )
        is KtCallExpression -> argumentExpression.valueArguments.mapNotNull { it.getArgumentExpression() as? KtClassLiteralExpression }
        else -> emptyList()
    }
}