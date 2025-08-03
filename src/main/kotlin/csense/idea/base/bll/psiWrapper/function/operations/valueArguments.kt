package csense.idea.base.bll.psiWrapper.function.operations

import csense.idea.base.bll.psiWrapper.annotation.*
import csense.idea.base.bll.psiWrapper.function.*
import org.jetbrains.kotlin.psi.*


fun KtPsiFunction.valueArguments(): List<KtPsiFunctionValueArgument> = when (this) {
    is KtPsiFunction.Kt -> valueArguments()
    is KtPsiFunction.Psi -> TODO()
}

fun KtPsiFunction.Kt.valueArguments(): List<KtPsiFunctionValueArgument> {
    return function.valueParameters.map { it: KtParameter ->
        it.toKtPsiFunctionValueArgument()
    }
}

fun KtParameter.toKtPsiFunctionValueArgument(

): KtPsiFunctionValueArgument = KtPsiFunctionValueArgument(
    name = name,
    type = TODO(),
    defaultValue = defaultValue?.toKtPsiExpression(),
    annotations = TODO()
)


data class KtPsiFunctionValueArgument(
    val name: String?,
    val type: KtPsiType,
    val defaultValue: KtPsiExpression?,
    val annotations: List<KtPsiAnnotation>,
)