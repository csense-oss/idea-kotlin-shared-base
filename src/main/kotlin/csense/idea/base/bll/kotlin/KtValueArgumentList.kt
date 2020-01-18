package csense.idea.base.bll.kotlin

import org.jetbrains.kotlin.psi.KtValueArgument
import org.jetbrains.kotlin.psi.KtValueArgumentList


fun KtValueArgumentList.clearArguments() {
    val count = arguments.size
    for (i in 0 until count) {
        removeArgument(0)
    }
}

fun KtValueArgumentList.replaceArguments(vararg newParameters: KtValueArgument) {
    clearArguments()
    addAllArguments(newParameters)
}

fun KtValueArgumentList.addAllArguments(newParameters: Array<out KtValueArgument>) {
    newParameters.forEach {
        addArgument(it)
    }
}


fun KtValueArgumentList.replaceArguments(newParameters: List<KtValueArgument>) {
    clearArguments()
    addAllArguments(newParameters)
}

fun KtValueArgumentList.addAllArguments(newParameters: List<KtValueArgument>) {
    newParameters.forEach {
        addArgument(it)
    }
}
