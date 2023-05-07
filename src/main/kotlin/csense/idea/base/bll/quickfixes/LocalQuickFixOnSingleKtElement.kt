package csense.idea.base.bll.quickfixes

import com.intellij.codeInspection.*
import com.intellij.openapi.project.*
import com.intellij.psi.*
import org.jetbrains.kotlin.psi.*

abstract class LocalQuickFixOnSingleKtElement<T : KtElement>(
    element: T
) : LocalQuickFixOnPsiElement(element) {

    @Suppress("ActionIsNotPreviewFriendly")
    val project: Project by lazy{
        element.project
    }

    @Suppress("UNCHECKED_CAST")
    final override fun invoke(project: Project, file: PsiFile, startElement: PsiElement, endElement: PsiElement) {
        val elementToUse: T = startElement as? T ?: return
        invoke(project = project, file = file, element = elementToUse)
    }

    abstract fun invoke(project: Project, file: PsiFile, element: T)
}
