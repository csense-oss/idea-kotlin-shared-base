@file:Suppress("unused")

package csense.idea.base.bll.kotlin

import org.jetbrains.kotlin.descriptors.CallableDescriptor
import org.jetbrains.kotlin.types.*

fun CallableDescriptor.resolveType(): KotlinType? = returnType

/**
 *
 * @return List<String> the order of the arguments as well as the name
 */
fun CallableDescriptor.findOriginalMethodArgumentNames(): List<String> {
    return valueParameters.map { param ->
        param.name.asString()
    }
}
