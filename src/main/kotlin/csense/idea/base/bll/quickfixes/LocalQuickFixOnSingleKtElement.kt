package csense.idea.base.bll.quickfixes

import com.intellij.codeInspection.*
import com.intellij.openapi.project.*
import com.intellij.psi.*
import csense.idea.base.bll.psi.*
import org.jetbrains.kotlin.psi.*

abstract class LocalQuickFixOnSingleKtElement<T : KtElement>(
    element: T
) : LocalQuickFix {

    @Suppress("ActionIsNotPreviewFriendly")
    val project: Project = element.project

    private val ref: SmartPsiElementPointer<T> = element.smartPsiElementPointer()

    override fun getName(): String {
        return getText()
    }

    override fun applyFix(project: Project, problem: ProblemDescriptor) {
        val element: T = ref.element ?: return
        invoke(project = project, file = element.containingFile, element = element)
    }

    abstract fun invoke(project: Project, file: PsiFile, element: T)
    abstract fun getText(): String
}