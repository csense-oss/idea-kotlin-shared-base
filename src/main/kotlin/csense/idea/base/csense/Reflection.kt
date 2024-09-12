package csense.idea.base.csense

import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.javaType


@OptIn(ExperimentalStdlibApi::class)
fun Collection<KFunction<*>>.findByNameAndParameterTypes(
    name: String,
    vararg typesFqNames: String,
): KFunction<*>? {
    return firstOrNull { it: KFunction<*> ->
        it.name == name &&
                it.parameters.size == typesFqNames.size &&
                it.parameters.allIndexed { index: Int, parameter: KParameter ->
                    typesFqNames[index] == parameter.type.javaType.typeName
                }
    }
}