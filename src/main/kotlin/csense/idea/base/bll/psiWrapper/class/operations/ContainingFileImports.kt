package csense.idea.base.bll.psiWrapper.`class`.operations

import com.intellij.psi.*
import csense.idea.base.bll.psiWrapper.`class`.*
import csense.idea.base.bll.psiWrapper.imports.*
import csense.idea.base.bll.psiWrapper.imports.operations.*
import org.jetbrains.kotlin.psi.*


fun KtPsiClass.containingFileImports(): List<KtPsiImports> = when (this) {
    is KtPsiClass.Kt -> containingFileImports()
    is KtPsiClass.Psi -> containingFileImports()
}

fun KtPsiClass.Kt.containingFileImports(): List<KtPsiImports.Kt> {
    val file: KtFile = ktClassOrObject.containingKtFile
    return file.ktPsiImports()
}


fun KtPsiClass.Psi.containingFileImports(): List<KtPsiImports> {
    val file: PsiFile = psiClass.containingFile
    return file.ktPsiImports()
}