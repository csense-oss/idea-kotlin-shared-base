package csense.idea.base.bll.psiWrapper.function.operations

import csense.idea.base.bll.psiWrapper.documentation.*
import csense.idea.base.bll.psiWrapper.function.*
import csense.kotlin.extensions.collections.*


fun KtPsiFunction.documentation(): KtPsiDoc? = when (this) {
    is KtPsiFunction.Kt -> documentation()
    is KtPsiFunction.Psi -> documentation()
}

fun KtPsiFunction.Kt.documentation(): KtPsiDoc? {
    val allDocLines: List<String> = function
        .docComment
        ?.getAllSections()
        ?.map { it.text }
        .nullOnEmpty() ?: return null

    return KtPsiDoc(allDocLines)
}

//TODO
fun KtPsiFunction.Psi.documentation(): KtPsiDoc? {
    return null
}


