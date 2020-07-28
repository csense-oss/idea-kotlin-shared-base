package csense.idea.base

inline fun <reified T, reified TT : T> T.mapIfInstance(action: Function1<TT, T>): T {
    return when (this) {
        is TT -> action(this)
        else -> this
    }
}