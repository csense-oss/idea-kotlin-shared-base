package csense.idea.base.bll.psiWrapper.imports.operations

import csense.idea.base.bll.psiWrapper.imports.*

fun KtPsiImports.isForType(type: String): Boolean {
    return import.endsWith(type)
}
