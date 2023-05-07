@file:Suppress("unused")

package csense.idea.base.bll.kotlin

import csense.idea.base.*
import csense.idea.base.bll.kotlin.models.*
import csense.kotlin.extensions.collections.*
import org.jetbrains.kotlin.psi.*


fun KtCallableDeclaration.parametersFromCallWithAnnotations(
    invocationSite: KtCallElement,
    filter: (ParameterToValueExpression) -> Boolean = { _: ParameterToValueExpression -> true }
): List<ParameterToValueExpression> {

    val parametersToCallValues: LinkedHashMap<String, ParameterToValueExpression> =
        resolveValueParametersTo(invocationSite)

    val result: MutableList<ParameterToValueExpression> = mutableListOf()
    parametersToCallValues.forEach { (_: String, parameterToValueExpression: ParameterToValueExpression) ->
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
): Unit = forEach { parameter: KtParameter ->
    val name: String = parameter.name ?: TODO("UNKNOWN ERROR for ${parameter.text}, expected it to have a name but got null")
    into[name] = MutableParameterToValueExpressions(
        parameter = parameter,
        valueArguments = listOfNotNull(parameter.defaultValue),
        parameterValueAnnotations = parameter.annotationEntries,
        parameterTypeAnnotations = parameter.typeReference?.annotationEntries.orEmpty()
    )
}

//Todo this code is ugly...
fun List<ValueArgument>.fillValueArgumentsInto(
    into: Map<String, MutableParameterToValueExpressions>
) {

    fun appendVarargArg(
        expression: MutableParameterToValueExpressions,
        argumentExpression: KtExpression?
    ) {
        //vararg => add each vararg as a separate expression
        expression.valueArguments += listOfNotNull(argumentExpression)
    }

    fun appendRegularArg(
        expression: MutableParameterToValueExpressions,
        argumentExpression: KtExpression?,
        onIncrementParameterIndex: () -> Unit
    ) {
        onIncrementParameterIndex()
        //non vararg => there should only be a single argument expression
        expression.valueArguments = listOfNotNull(argumentExpression)
    }

    fun appendArg(
        expression: MutableParameterToValueExpressions,
        argumentExpression: KtExpression?,
        onIncrementParameterIndex: () -> Unit
    ) {
        val isTrackingVarArg: Boolean = expression.parameter.isVarArg
        if (isTrackingVarArg) {
            appendVarargArg(expression, argumentExpression)
            return
        }
        appendRegularArg(expression, argumentExpression, onIncrementParameterIndex)
    }


    fun tryAppendArg(
        expression: MutableParameterToValueExpressions?,
        argumentExpression: KtExpression?,
        onIncrementParameterIndex: () -> Unit
    ) {
        val expression: MutableParameterToValueExpressions = expression ?: return
        appendArg(
            expression = expression,
            argumentExpression = argumentExpression,
            onIncrementParameterIndex = onIncrementParameterIndex
        )
    }

    var parameterIndex = 0
    //the rules of argument order can be described as such:
    //-if named then that precedes all (allows to mix'n match all orders)
    //otherwise it must be that up-to eventual all named is index based (you cannot have: a default,  a named followed by some without name)
    // You can have correct position and add names to some (as long as it is positionally correct)
    //- thus position is secondary if not named.
    //- default arguments are thus "last" in the order (either not set, or overwritten)
    //TODO SAM functions..
    forEach { argument: ValueArgument ->
        val argName: String? = argument.getArgumentNameAsString()
        if (argName != null) {
            parameterIndex += 1
            into[argName]?.valueArguments = listOfNotNull(argument.getArgumentExpression())
            return@forEach
        }

        //a special rule: there may be a lambda arg following a method call. (the last argument & it is a lambda)
        //thus the loop should be done after this.
        val isLambdaOutsideOfFunctionCall: Boolean = argument is KtLambdaArgument && isLastIndex(parameterIndex)
        if (isLambdaOutsideOfFunctionCall) {
            tryAppendArg(
                expression = into.entries.lastOrNull()?.value,
                argumentExpression = argument.getArgumentExpression(),
                onIncrementParameterIndex = {})
            return@forEach
        }
        tryAppendArg(
            expression = into.entries.getOrNull(parameterIndex)?.value,
            argumentExpression = argument.getArgumentExpression(),
            onIncrementParameterIndex = { parameterIndex += 1 })
    }

}