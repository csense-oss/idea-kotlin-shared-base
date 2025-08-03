package csense.idea.base.bll.psi

import com.intellij.openapi.project.*
import com.intellij.psi.*

fun <E : PsiElement> E.smartPsiElementPointer(): SmartPsiElementPointer<E> =
    smartPsiElementPointer(project, containingFile)

fun <E : PsiElement> E.smartPsiElementPointer(project: Project, file: PsiFile): SmartPsiElementPointer<E> =
    SmartPointerManager.getInstance(project).createSmartPsiElementPointer(this, file)