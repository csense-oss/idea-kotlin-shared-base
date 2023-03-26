package csense.idea.base.bll.psiWrapper.function.operations

import com.intellij.psi.*
import csense.idea.base.bll.kotlin.*
import csense.idea.base.bll.psiWrapper.function.*
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.psi.psiUtil.*

fun KtPsiFunction.findCallsInPlaceContracts(): List<KtCallExpression> = when (this) {
    is KtPsiFunction.Kt -> function.findCallsInPlaceContracts()
    is KtPsiFunction.Psi -> function.findCallsInPlaceContracts()
}

fun PsiMethod.findCallsInPlaceContracts(): List<KtCallExpression> =
    emptyList()

fun KtFunction.findCallsInPlaceContracts(

): List<KtCallExpression> = collectDescendantsOfType { callExpression: KtCallExpression ->
    callExpression.resolveMainReferenceAsKtFunction()?.fqName?.asString() == "kotlin.contracts.ContractBuilder.callsInPlace"
}