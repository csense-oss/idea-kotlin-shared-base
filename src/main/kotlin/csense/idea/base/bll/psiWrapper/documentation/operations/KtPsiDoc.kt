package csense.idea.base.bll.psiWrapper.documentation.operations

import csense.idea.base.bll.psiWrapper.documentation.*

fun KtPsiDoc.findAnnotations(searchString: String, ignoreCase: Boolean = false): List<KtPsiDocElement> {
    return docList.filter { it.contains("@$searchString", ignoreCase = ignoreCase) }.map { it: String ->
        KtPsiDocElement(it)
    }
}