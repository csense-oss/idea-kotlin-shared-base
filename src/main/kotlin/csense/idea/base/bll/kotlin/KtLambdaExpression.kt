@file:Suppress("unused")

package csense.idea.base.bll.kotlin

import csense.idea.base.bll.kotlin.models.*
import csense.idea.base.bll.psiWrapper.function.*
import csense.idea.base.bll.psiWrapper.function.operations.*
import csense.kotlin.extensions.collections.*
import org.jetbrains.kotlin.idea.quickfix.createFromUsage.callableBuilder.TypeInfo
import org.jetbrains.kotlin.idea.quickfix.createFromUsage.callableBuilder.getParameterInfos
import org.jetbrains.kotlin.psi.*

fun KtLambdaExpression.resolveParameterIndex(): Int? {
    val callExp: KtCallExpression = resolveParentCallExpression() ?: return null
    return callExp.getParameterInfos().indexOfFirst {
        (it.typeInfo as? TypeInfo.ByExpression)?.expression === this
    }
}

fun KtLambdaExpression.resolveParameterFunction(): KtPsiFunction? {
    return resolveParentCallExpression()?.resolveMainReferenceAsFunction()
}

fun KtLambdaExpression.resolveParentCallExpression(): KtCallExpression? {
    return parent?.parent as? KtCallExpression
        ?: parent?.parent?.parent as? KtCallExpression
}

fun KtLambdaExpression.isCallInPlace(): Boolean {
    val contracts: List<KtCallExpression> =
        resolveParameterFunction()?.findCallsInPlaceContracts()?.nullOnEmpty() ?: return false

    //TODO SUB_OPTIMAL PERF ETC...
    val lambdaParameterToExpression: ParameterToValueExpression = toParameterToValueExpression() ?: return false
    val lambdaParameterName = lambdaParameterToExpression.parameter.name ?: return false
    return contracts.any { contract: KtCallExpression ->
        contract.valueArguments.any { contractArgument: KtValueArgument -> contractArgument.text == lambdaParameterName }
    }

}

fun KtLambdaExpression.toParameterToValueExpression(): ParameterToValueExpression? {

    val callExp: KtCallExpression = resolveParentCallExpression() ?: return null
    val mainFnc: KtFunction = callExp.resolveMainReferenceAsKtFunction() ?: return null
    //TODO this is quite... fcked.. see if it can be cleaned /refactored to make some more sense
    val callToParameters: List<ParameterToValueExpression> = mainFnc.parametersFromCallWithAnnotations(callExp)

    return callToParameters.firstOrNull { it: ParameterToValueExpression ->
        it.valueArguments.any { argumentExpressions -> argumentExpressions == this }
    }
}