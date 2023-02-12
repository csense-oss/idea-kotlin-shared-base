package csense.idea.base.bll

import org.jetbrains.kotlin.descriptors.*

/**
 *
 * @return List<String> the order of the arguments as well as the name
 */
fun CallableDescriptor.findOriginalMethodArgumentNames(): List<String> {
    return valueParameters.map { param ->
        param.name.asString()
    }
}
