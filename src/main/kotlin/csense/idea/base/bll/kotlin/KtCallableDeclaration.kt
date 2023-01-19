@file:Suppress("unused")

package csense.idea.base.bll.kotlin

import csense.idea.base.*
import csense.idea.base.bll.kotlin.models.*
import csense.kotlin.extensions.collections.*
import csense.kotlin.extensions.collections.generic.*
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
): LinkedHashMap<String, ParameterToValueExpression> =
    valueParameters.resolveValueParametersTo(arguments = callExpression.valueArguments)

fun List<KtParameter>.resolveValueParametersTo(
    arguments: List<ValueArgument>
): LinkedHashMap<String, ParameterToValueExpression> {
    val result = LinkedHashMap<String, MutableParameterToValueExpressions>()
    fillValueParametersInto(into = result)
    arguments.fillValueArgumentsInto(into = result)
    return result as LinkedHashMap<String, ParameterToValueExpression>
}

fun List<KtParameter>.fillValueParametersInto(
    into: MutableMap<String, MutableParameterToValueExpressions>
) = forEach { parameter ->
    val name =
        parameter.name ?: TODO("UNKNOWN ERROR for ${parameter.text}, expected it to have a name but got null")
    into[name] = MutableParameterToValueExpressions(
        parameter = parameter,
        valueArguments = listOfNotNull(parameter.defaultValue),
        parameterValueAnnotations = parameter.annotationEntries,
        parameterTypeAnnotations = parameter.typeReference?.annotationEntries.orEmpty()
    )
}


fun List<ValueArgument>.fillValueArgumentsInto(
    into: Map<String, MutableParameterToValueExpressions>
) {
    //the rules of argument order can be described as such:
    //-if named then that precedes all (allows to mix'n match all orders)
    //otherwise it must be that up-to eventual all named is index based (you cannot have: a default,  a named followed by some without name)
    // You can have correct position and add names to some (as long as it is positionally correct)
    //- thus position is secondary if not named.
    //- default arguments are thus "last" in the order (either not set, or overwritten)


    //TODO is it better to do a for loop at this point?

    var parameterIndex = 0

    forEach { argument ->
        val argName = argument.getArgumentName()?.asName?.asString()
        if (argName != null) {
            parameterIndex += 1
            into[argName]?.valueArguments = listOfNotNull(argument.getArgumentExpression())
            return@forEach
        }

        //a special rule: there may be a lambda arg following a method call. (the last argument & it is a lambda)
        //thus the loop should be done after this.
        val isLambdaOutsideOfFunctionCall = argument is KtLambdaArgument && isLastIndex(parameterIndex)
        if (isLambdaOutsideOfFunctionCall) {
            into.entries.lastOrNull()?.value?.valueArguments = listOfNotNull(argument.getArgumentExpression())
            return@forEach
        }


        val parameter = into.entries.getOrNull(parameterIndex)
        if (parameter == null) {
            TODO("log error; index = $parameterIndex, entries: ${into.entries}")
        }
        //vararg code.... and index based.
        //Todo this code is ugly...
        val isTrackingVarArg = parameter.value.parameter.isVarArg
        if (!isTrackingVarArg) {
            parameterIndex += 1
            //non vararg => there should only be a single argument expression
            parameter.value.valueArguments = listOfNotNull(argument.getArgumentExpression())
        } else {
            //vararg => we add each vararg as a separate expression
            parameter.value.valueArguments += listOfNotNull(argument.getArgumentExpression())
        }
    }
}


//fun manyParams(a: Int = 11, b: Int, c: Int, vararg d: Int) {
//
//}
//
//fun useParams() {
//    manyParams(
//        b = 42,
//        c = 1
//    )
//}