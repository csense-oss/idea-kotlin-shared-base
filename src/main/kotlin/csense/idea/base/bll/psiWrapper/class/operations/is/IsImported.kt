package csense.idea.base.bll.psiWrapper.`class`.operations.`is`

import com.intellij.psi.*
import csense.idea.base.bll.psiWrapper.`class`.*
import csense.idea.base.bll.psiWrapper.`class`.operations.*
import csense.idea.base.bll.psiWrapper.imports.*
import csense.idea.base.bll.psiWrapper.imports.operations.*

fun KtPsiClass.isImportedIn(file: PsiFile): Boolean {
    val fqName: String = fqName ?: return false
    val imports: List<KtPsiImports> = file.ktPsiImports()
    return imports.any { it: KtPsiImports ->
        it.contains(
            fqName = fqName,
            className = className()
        )
    }
}

