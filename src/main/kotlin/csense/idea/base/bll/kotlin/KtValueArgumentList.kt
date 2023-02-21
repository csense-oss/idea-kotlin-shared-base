@file:Suppress("unused")

package csense.idea.base.bll.kotlin

import csense.idea.base.bll.psiWrapper.`class`.*
import csense.idea.base.bll.psiWrapper.`class`.operations.*
import org.jetbrains.kotlin.psi.KtValueArgument
import org.jetbrains.kotlin.psi.KtValueArgumentList


fun KtValueArgumentList.clearArguments() {
    val count: Int = arguments.size
    for (i: Int in 0 until count) {
        removeArgument(0)
    }
}

fun KtValueArgumentList.replaceArguments(vararg newParameters: KtValueArgument) {
    clearArguments()
    addAllArguments(newParameters)
}

fun KtValueArgumentList.addAllArguments(newParameters: Array<out KtValueArgument>) {
    newParameters.forEach { it: KtValueArgument ->
        addArgument(it)
    }
}


fun KtValueArgumentList.replaceArguments(newParameters: List<KtValueArgument>) {
    clearArguments()
    addAllArguments(newParameters)
}

fun KtValueArgumentList.addAllArguments(newParameters: List<KtValueArgument>) {
    newParameters.forEach { it: KtValueArgument ->
        addArgument(it)
    }
}

fun KtValueArgumentList.addTypeRefs(types: List<KtPsiClass>) {
    val newTypesToAdd: List<KtValueArgument> = types.toFqNameRefValueArguments(project)
    addAllArguments(newTypesToAdd)
}
