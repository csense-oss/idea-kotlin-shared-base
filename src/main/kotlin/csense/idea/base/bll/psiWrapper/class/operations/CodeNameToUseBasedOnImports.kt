package csense.idea.base.bll.psiWrapper.`class`.operations

import com.intellij.psi.*
import csense.idea.base.bll.psiWrapper.`class`.*
import csense.idea.base.bll.psiWrapper.`class`.operations.`is`.*
import org.jetbrains.kotlin.psi.*

//TODO better name..?
fun KtPsiClass.codeNameToUseBasedOnImports(file: PsiFile): String {
    val fqName: String = fqName ?: return ""
    return when (isImportedIn(file)) {
        true -> className()
        false -> fqName
    }
}