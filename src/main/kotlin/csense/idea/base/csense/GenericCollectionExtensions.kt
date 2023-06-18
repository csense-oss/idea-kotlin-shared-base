package csense.idea.base.csense

import csense.kotlin.extensions.collections.generic.*

fun <T> GenericCollectionExtensions.walkUpAny(
    startingPoint: T,
    getToNextLevelOrNull: (T) -> T?,
    predicate: (T) -> Boolean
): Boolean {
    GenericCollectionExtensions.walkUp(
        startingPoint = startingPoint,
        getToNextLevelOrNull = getToNextLevelOrNull,
        onEachLevel = { it: T ->
            val has: Boolean = predicate(it)
            if (has) {
                return@walkUpAny true
            }
        }
    )
    return false
}


inline fun <T> GenericCollectionExtensions.walkUpFirstOrNull(
    startingPoint: T,
    getToNextLevelOrNull: (T) -> T?,
    predicate: (T) -> Boolean
): T? {
    walkUp(startingPoint, getToNextLevelOrNull) {
        if (predicate(it)) {
            return@walkUpFirstOrNull it
        }
    }
    return null
}

inline fun <T> GenericCollectionExtensions.walkUp(
    startingPoint: T,
    getToNextLevelOrNull: (T) -> T?,
    onEachLevel: (T) -> Unit
) {
    var currentElement: T? = getToNextLevelOrNull(startingPoint)
    while (currentElement != null) {
        onEachLevel(currentElement)
        currentElement = getToNextLevelOrNull(currentElement)
    }
}


inline fun <T> GenericCollectionExtensions.walkUpWithAny(
    startingPoint: T,
    getToNextLevelOrNull: (T) -> T?,
    predicate: (T) -> Boolean
): Boolean {
    GenericCollectionExtensions.walkUpWith(
        startingPoint = startingPoint,
        getToNextLevelOrNull = getToNextLevelOrNull,
        onEachLevel = { it: T ->
            val has: Boolean = predicate(it)
            if (has) {
                return@walkUpWithAny true
            }
        }
    )
    return false
}


inline fun <T> GenericCollectionExtensions.walkUpWith(
    startingPoint: T,
    getToNextLevelOrNull: (T) -> T?,
    onEachLevel: (T) -> Unit
) {
    var currentElement: T? = startingPoint
    while (currentElement != null) {
        onEachLevel(currentElement)
        currentElement = getToNextLevelOrNull(currentElement)
    }
}

