@file:Suppress("unused")

package csense.idea.base.bll.kotlin

import csense.idea.base.bll.kotlin.models.*
import csense.kotlin.extensions.collections.*
import csense.kotlin.extensions.collections.map.*
import org.jetbrains.kotlin.psi.*



fun KtCallableDeclaration.parametersFromCallWithAnnotations(
    invocationSite: KtCallElement,
    filter: (ParameterToValueExpression) -> Boolean = { _ -> true }
): List<ParameterToValueExpression> {
    val result: MutableList<ParameterToValueExpression> = mutableListOf()

    val parametersToCallValues: LinkedHashMap<String, ParameterToValueExpression> =
        resolveValueParametersTo(invocationSite)

    parametersToCallValues.forEachIndexed { (_: String, parameterToValueExpression: ParameterToValueExpression), index ->
        result.addIf(
            condition = filter(parameterToValueExpression),
            item = parameterToValueExpression
        )
    }

    return result
}


fun KtCallableDeclaration.resolveValueParametersTo(
    callExpression: KtCallElement
): LinkedHashMap<String, ParameterToValueExpression> {
    val result = LinkedHashMap<String, MutableParameterToValueExpression>()
    fillValueParametersInto(into = result)
    callExpression.fillValueArgumentsInto(into = result)
    return result as LinkedHashMap<String, ParameterToValueExpression>
}

fun KtCallableDeclaration.fillValueParametersInto(
    into: MutableMap<String, MutableParameterToValueExpression>
) {
    valueParameters.forEach { parameter ->
        val name = parameter.name ?: return@forEach //TODO when /  how can this happen??
        val annotations = parameter.annotationEntries
        into[name] = MutableParameterToValueExpression(
            parameter = parameter,
            valueArgument = parameter.defaultValue,
            parameterAnnotations = annotations
        )
    }
}

//@SideEffects
fun KtCallElement.fillValueArgumentsInto(into: Map<String, MutableParameterToValueExpression>) {
    //the rules of argument order can be described as such:
    //-if named then that precedes all (allows to mix'n match all orders)
    //otherwise it must be that up-to eventual all named is index based (you cannot have: a default,  a named followed by some without name)
    // You can have correct position and add names to some (as long as it is positionally correct)
    //- thus position is secondary if not named.
    //- default arguments are thus "last" in the order (either not set, or overwritten)
    valueArguments.forEachIndexed { index, argument ->
        val argName = argument.getArgumentName()?.asName?.asString()
        if (argName != null) {
            into[argName]?.valueArgument = argument.getArgumentExpression()
            return@forEachIndexed
        }
        val parameter = into.entries.getOrNull(index)
        if (parameter == null) {
            TODO("log error")
        }
        parameter.value.valueArgument = argument.getArgumentExpression()
    }
}
