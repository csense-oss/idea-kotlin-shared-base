package csense.idea.base.bll.kotlin

import org.jetbrains.kotlin.psi.KtDotQualifiedExpression
import org.jetbrains.kotlin.psi.KtElement

tailrec fun KtDotQualifiedExpression.rightMostSelectorExpression(): KtElement? {
    val selector = selectorExpression
    if (selector is KtDotQualifiedExpression) {
        return selector.rightMostSelectorExpression()
    }
    return selector
}