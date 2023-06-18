@file:Suppress("NOTHING_TO_INLINE", "unused", "UnusedReceiverParameter", "RedundantVisibilityModifier")
@file:OptIn(ExperimentalContracts::class)

package csense.idea.base.csense

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

