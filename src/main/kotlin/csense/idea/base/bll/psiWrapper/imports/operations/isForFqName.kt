package csense.idea.base.bll.psiWrapper.imports.operations

import csense.idea.base.bll.psiWrapper.imports.*

fun KtPsiImports.isForFqName(fqName: String): Boolean {
    return import == fqName || isStarImportFor(fqName)
}

fun KtPsiImports.isStarImportFor(fqName: String): Boolean {
    return isStarImport() && fqName.asStarImport() == import
}

private fun String.asStarImport(): String {
    return replaceAfterLast(delimiter = ".", replacement = "*")
}