@file:Suppress("unused")

package csense.idea.base.bll.kotlin

import csense.idea.base.bll.psi.*
import csense.idea.base.csense.*
import csense.kotlin.extensions.collections.generic.*
import org.jetbrains.kotlin.psi.*

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

fun <T> KtElement.selectParentByOrNull(
    selectAction: (KtElement) -> T?
): T? = GenericCollectionExtensions.selectFirstOrNull(
    startingPoint = this,
    selectAction = selectAction,
    goToNextLevel = { it: KtElement -> it.ktParent }
)


fun <Tree, Select> GenericCollectionExtensions.selectFirstOrNull(
    startingPoint: Tree,
    selectAction: (Tree) -> Select?,
    goToNextLevel: (Tree) -> Tree?
): Select? {
    GenericCollectionExtensions.walkUp(
        startingPoint = startingPoint,
        getToNextLevelOrNull = goToNextLevel,
        onEachLevel = { it: Tree ->
            val result: Select? = selectAction(it)
            if (result != null) {
                return@selectFirstOrNull result
            }
        }
    )
    return null
}