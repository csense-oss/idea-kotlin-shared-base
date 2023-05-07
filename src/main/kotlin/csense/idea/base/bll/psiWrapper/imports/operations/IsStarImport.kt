package csense.idea.base.bll.psiWrapper.imports.operations

import csense.idea.base.bll.psiWrapper.imports.*

fun KtPsiImports.isStarImport(): Boolean {
    return import.endsWith(".*")
}
