package csense.idea.base.csense


fun <T> Collection<T>.allIndexed(predicate: (Int, T) -> Boolean): Boolean {
    var index = 0
    return all { it: T ->
        predicate(index, it).also {
            index += 1
        }
    }
}