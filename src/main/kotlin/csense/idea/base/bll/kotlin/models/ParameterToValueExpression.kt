package csense.idea.base.bll.kotlin.models

import org.jetbrains.kotlin.psi.*


sealed interface ParameterToValueExpression {
    val parameter: KtParameter
    val valueArguments: List<KtExpression>
    val parameterValueAnnotations: List<KtAnnotationEntry>
    val parameterTypeAnnotations: List<KtAnnotationEntry>
}

val ParameterToValueExpression.allAnnotations: List<KtAnnotationEntry>
    get() = parameterValueAnnotations + parameterTypeAnnotations

fun ParameterToValueExpression(
    parameter: KtParameter,
    valueArguments: List<KtExpression>,
    parameterValueAnnotations: List<KtAnnotationEntry>,
    parameterTypeAnnotations: List<KtAnnotationEntry>
): ParameterToValueExpression = MutableParameterToValueExpressions(
    parameter = parameter,
    valueArguments = valueArguments,
    parameterValueAnnotations = parameterValueAnnotations,
    parameterTypeAnnotations = parameterTypeAnnotations
)

data class MutableParameterToValueExpressions(
    override var parameter: KtParameter,
    override var valueArguments: List<KtExpression>,
    override var parameterValueAnnotations: List<KtAnnotationEntry>,
    override val parameterTypeAnnotations: List<KtAnnotationEntry>

) : ParameterToValueExpression

