@file:Suppress("NOTHING_TO_INLINE", "unused", "UnusedReceiverParameter", "RedundantVisibilityModifier")
@file:OptIn(ExperimentalContracts::class)

package csense.idea.base

import csense.kotlin.extensions.collections.generic.*
import kotlin.contracts.*
import kotlin.experimental.*

public inline fun Collection<*>.isLastIndex(
    index: Int
): Boolean {
    return size - 1 == index
}

@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
public inline fun <T, CollectionType : Collection<T>> CollectionType.onEmpty(
    otherCollection: CollectionType
): CollectionType = when (isEmpty()) {
    true -> otherCollection
    false -> this
}

public inline fun <T, CollectionType : Collection<T>> CollectionType.onEmpty(
    action: () -> CollectionType
): CollectionType {
    contract {
        callsInPlace(action, InvocationKind.AT_MOST_ONCE)
    }
    return when (isEmpty()) {
        true -> action()
        false -> this
    }
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

