@file:Suppress("NOTHING_TO_INLINE", "unused")
@file:OptIn(ExperimentalContracts::class)

package csense.idea.base

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

