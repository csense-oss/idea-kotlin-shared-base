@file:Suppress("unused")

package csense.idea.base.bll.kotlin

import csense.idea.base.bll.psi.findParentOfType
import csense.idea.base.csense.*
import csense.kotlin.extensions.collections.generic.*
import org.jetbrains.kotlin.psi.KtClassOrObject
import org.jetbrains.kotlin.psi.KtElement
import org.jetbrains.kotlin.psi.KtObjectDeclaration

fun KtElement.isInObject(): Boolean =
    this.findParentOfType<KtClassOrObject>() is KtObjectDeclaration

inline val KtElement.ktParent: KtElement?
    get() = parent as? KtElement


fun KtElement.firstParentByOrNull(
    predicate: (KtElement) -> Boolean
): KtElement? = GenericCollectionExtensions.walkUpFirstOrNull(
    startingPoint = this,
    getToNextLevelOrNull = { it: KtElement -> it.ktParent },
    predicate = predicate
)
