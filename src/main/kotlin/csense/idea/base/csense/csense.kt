@file:Suppress("NOTHING_TO_INLINE", "unused", "UnusedReceiverParameter", "RedundantVisibilityModifier")
@file:OptIn(ExperimentalContracts::class)

package csense.idea.base.csense

import csense.kotlin.extensions.collections.*
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


fun <T, R> Iterable<T>.filterMapped(
    predicate: (R) -> Boolean,
    transform: (T) -> R
): List<R> = createListBy { result: MutableList<R> ->
    forEach { it: T ->
        val transformed: R = transform(it)
        result.addIf(
            condition = predicate(transformed),
            item = transformed
        )
    }
}

inline fun <T> createListBy(builder: (result: MutableList<T>) -> Unit): List<T> {
    contract {
        callsInPlace(builder, InvocationKind.EXACTLY_ONCE)
    }
    val result: MutableList<T> = mutableListOf()
    builder(result)
    return result
}

inline fun <T, R> Array<T>.filterMapped(
    predicate: (R) -> Boolean,
    transform: (T) -> R
): List<R> {
//    return GenericCollectionExtensions.filterMapped(
//        this::forEach,
//        predicate,
//        transform
//    )
    return createListBy { result: MutableList<R> ->
        forEach { it: T ->
            val transformed: R = transform(it)
            result.addIf(
                condition = predicate(transformed),
                item = transformed
            )
        }
    }
}


//public inline fun <T, R> GenericCollectionExtensions.filterMapped(
//    forEach: ((item: T) -> Unit) -> Unit,
//    predicate: (R) -> Boolean,
//     transform: (T) -> R
//): List<R> {
//    val result: MutableList<R> = mutableListOf()
//    forEach { item: T ->
//        val transformed: R =transform(item)
//        result.addIf(
//            condition = predicate(transformed),
//            item = transformed
//        )
//    }
//    return result
//}

//TODO use from csense 0.1.0
@Suppress("UNCHECKED_CAST")
fun <T> Class<T>.tryCast(other: Any): T? = when {
    other::class.java.isAssignableFrom(this) -> other as T
    else -> null
}


/**
 * TODO?
 * An alternative to "?.let"
 * @receiver T?
 * @param action Function0<R>
 * @return R?
 */
inline fun <T, R> T?.onNotNull(
    action: (T) -> R,
): R? {
    contract {
        callsInPlace(action, InvocationKind.AT_MOST_ONCE)
    }
    return when (this) {
        null -> null
        else -> action(this)
    }
}