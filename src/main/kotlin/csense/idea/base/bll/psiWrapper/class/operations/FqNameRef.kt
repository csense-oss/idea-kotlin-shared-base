package csense.idea.base.bll.psiWrapper.`class`.operations

import com.intellij.psi.*
import csense.idea.base.bll.psiWrapper.`class`.*

fun KtPsiClass.fqNameRef(): String =
    "$fqName::class"

fun KtPsiClass.nameRef(file: PsiFile): String {
    val name: String = codeNameToUseBasedOnImports(file)
    return "$name::class"
}