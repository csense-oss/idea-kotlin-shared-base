package csense.idea.base

public inline fun Collection<*>.isLastIndex(
    index: Int
): Boolean {
    return size - 1 == index
}