package csense.idea.base.bll.psiWrapper.function.operations

import csense.idea.base.bll.psiWrapper.function.*
import org.jetbrains.kotlin.psi.*


fun KtPsiFunction.valueArgumentNames(): List<String> = when (this) {
    is KtPsiFunction.Kt -> valueArgumentNames()
    is KtPsiFunction.Psi -> valueArgumentNames()
}

fun KtPsiFunction.Kt.valueArgumentNames(): List<String> {
    return function.valueParameters.mapNotNull { it: KtParameter ->
        it.name
    }
}

fun KtPsiFunction.Psi.valueArgumentNames(): List<String> {
    TODO()
}