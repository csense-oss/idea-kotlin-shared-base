package csense.idea.base.bll.psiWrapper.imports.operations

import csense.idea.base.bll.psiWrapper.imports.*
import org.jetbrains.kotlin.psi.*

fun KtImportDirective.toKtPsiImportsOrNull(): KtPsiImports.Kt? {
    val pathString: String = importPath?.pathStr ?: return null
    return KtPsiImports.Kt(import = pathString)
}