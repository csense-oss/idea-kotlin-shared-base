package csense.idea.base.bll.psiWrapper.function.operations

import csense.idea.base.bll.kotlin.*
import csense.idea.base.bll.psiWrapper.function.*
import csense.kotlin.extensions.*
import org.jetbrains.kotlin.psi.KtCallExpression
import org.jetbrains.kotlin.psi.KtFunction
import org.jetbrains.kotlin.psi.psiUtil.*

fun KtPsiFunction.findCallsInPlaceContracts(): List<KtCallExpression> {
    if (this.isNot<KtPsiFunction.Kt>()) {
        return emptyList()
    }
    val function: KtFunction = this.function
    return function.collectDescendantsOfType { callExpression: KtCallExpression ->
        callExpression.resolveMainReferenceAsKtFunction()?.fqName?.asString() == "kotlin.contracts.ContractBuilder.callsInPlace"
    }
}