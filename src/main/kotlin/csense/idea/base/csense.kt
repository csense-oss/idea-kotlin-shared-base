@file:Suppress("NOTHING_TO_INLINE")

package csense.idea.base

public inline fun Collection<*>.isLastIndex(
    index: Int
): Boolean {
    return size - 1 == index
}

public inline fun <T> Collection<T>.onEmpty(
    otherCollection: Collection<T>
): Collection<T> = when (isEmpty()) {
    true -> otherCollection
    false -> this
}