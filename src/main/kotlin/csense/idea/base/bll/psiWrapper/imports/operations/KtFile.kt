package csense.idea.base.bll.psiWrapper.imports.operations

import com.intellij.psi.*
import csense.idea.base.bll.psiWrapper.imports.*
import org.jetbrains.kotlin.psi.*

fun KtFile.ktPsiImports(): List<KtPsiImports.Kt> {
    return importList?.imports?.mapNotNull(KtImportDirective::toKtPsiImportsOrNull).orEmpty()
}


fun PsiFile.ktPsiImports(): List<KtPsiImports> {
    if (this is KtFile) {
        return ktPsiImports()
    }

    TODO()
}