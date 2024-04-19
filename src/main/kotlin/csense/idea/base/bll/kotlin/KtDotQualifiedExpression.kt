package csense.idea.base.bll.kotlin

import org.jetbrains.kotlin.psi.*

tailrec fun KtDotQualifiedExpression.rightMostSelectorExpression(): KtElement? {
    val selector: KtExpression? = selectorExpression
    if (selector is KtDotQualifiedExpression) {
        return selector.rightMostSelectorExpression()
    }
    return selector
}

tailrec fun KtDotQualifiedExpression.leftMostSelectorExpression(): KtElement? {
    val selector: KtExpression = receiverExpression
    if (selector is KtDotQualifiedExpression) {
        return selector.leftMostSelectorExpression()
    }
    return selector
}