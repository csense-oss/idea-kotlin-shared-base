package csense.idea.base.bll.psiWrapper.imports.operations

import com.intellij.psi.*
import csense.idea.base.bll.psiWrapper.imports.*
import org.jetbrains.kotlin.psi.psiUtil.*


fun PsiFile.allImportsFqNames(): List<KtPsiImports> {
    val allImports: MutableList<KtPsiImports> = mutableListOf()
    forEachDescendantOfType<PsiImportList> { importList: PsiImportList ->
        allImports += importList.importStatements.mapNotNull { it: PsiImportStatement ->
            KtPsiImports.Psi(it.qualifiedName ?: return@mapNotNull null)
        }
    }
    return allImports
}