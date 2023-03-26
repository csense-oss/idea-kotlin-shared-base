@file:Suppress("unused")

package csense.idea.base.bll.kotlin

import csense.idea.base.bll.kotlin.models.*
import csense.idea.base.bll.psi.*
import csense.idea.base.bll.psiWrapper.function.*
import csense.idea.base.bll.psiWrapper.function.operations.*
import csense.kotlin.extensions.collections.*
import org.jetbrains.kotlin.psi.*



fun KtLambdaExpression.toLamdaArgumentLookup(): LambdaArgumentLookup? {

    val callExp: KtCallExpression = resolveParentCallExpression() ?: return null
    val mainFnc: KtFunction = callExp.resolveMainReferenceAsKtFunction() ?: return null
    //TODO this is quite... fcked.. see if it can be cleaned /refactored to make some more sense
    val callToParameters: List<ParameterToValueExpression> = mainFnc.parametersFromCallWithAnnotations(callExp)

    val parameter: ParameterToValueExpression = callToParameters.firstOrNull { it: ParameterToValueExpression ->
        it.valueArguments.any { argumentExpressions: KtExpression -> argumentExpressions == this }
    } ?: return null
    return LambdaArgumentLookup(parentFunction = mainFnc, parameterToValueExpression = parameter)
}

private fun KtLambdaExpression.resolveParentCallExpression(): KtCallExpression? {
    return parent?.parent as? KtCallExpression
        ?: parent?.parent?.parent as? KtCallExpression
}

fun LambdaArgumentLookup.isCallInPlace(): Boolean {
    val contracts: List<KtCallExpression> = parentFunction.findCallsInPlaceContracts()
    if (contracts.isEmpty()) {
        return false
    }
    return contracts.any { contract: KtCallExpression ->
        contract.valueArguments.any { contractArgument: KtValueArgument -> contractArgument.text == parameterName }
    }
}

data class LambdaArgumentLookup(
    val parentFunction: KtFunction,
    val parameterToValueExpression: ParameterToValueExpression
) {
    val parentFunctionFqName: String? by lazy {
        parentFunction.getKotlinFqNameString()
    }

    val parameterName: String? by lazy {
        parameterToValueExpression.parameter.name
    }
}