package csense.idea.base.bll.kotlin.models

import csense.idea.base.bll.kotlin.*
import org.jetbrains.kotlin.psi.*


sealed interface ParameterToValueExpression {
    val parameter: KtParameter
    val valueArgument: KtExpression?
    val parameterAnnotations: List<KtAnnotationEntry>
}

fun ParameterToValueExpression(
    parameter: KtParameter,
    valueArgument: KtExpression?,
    annotations: List<KtAnnotationEntry>
): ParameterToValueExpression = MutableParameterToValueExpression(
    parameter = parameter,
    valueArgument = valueArgument,
    parameterAnnotations = annotations
)

data class MutableParameterToValueExpression(
    override var parameter: KtParameter,
    override var valueArgument: KtExpression?,
    override var parameterAnnotations: List<KtAnnotationEntry>
) : ParameterToValueExpression

