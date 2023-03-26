package csense.idea.base.bll.kotlin

import csense.idea.base.bll.psiWrapper.`class`.*
import csense.idea.base.bll.psiWrapper.`class`.operations.*
import org.jetbrains.kotlin.psi.KtCallExpression
import org.jetbrains.kotlin.psi.KtClassLiteralExpression
import org.jetbrains.kotlin.psi.KtCollectionLiteralExpression
import org.jetbrains.kotlin.psi.ValueArgument

//fun ValueArgument.resolveClassLiterals(): List<KtClassLiteralExpression> {
//    return when (val argumentExpression = getArgumentExpression()) {
//        is KtClassLiteralExpression -> listOf(argumentExpression)
//        is KtCollectionLiteralExpression -> argumentExpression.getInnerExpressions().filterIsInstance(
//            KtClassLiteralExpression::class.java
//        )
//        is KtCallExpression -> argumentExpression.valueArguments.mapNotNull { it.getArgumentExpression() as? KtClassLiteralExpression }
//        else -> emptyList()
//    }
//}


fun List<ValueArgument>.resolveAsKClassTypes(): List<KtPsiClass> {
    return mapNotNull { annotation: ValueArgument ->
        //TODO. if for some cool reason the code contains "arrayOf" or alike (with star operator etc) then this will fail badly.
        annotation.getArgumentExpression()?.resolveFirstClassType2()
    }
}