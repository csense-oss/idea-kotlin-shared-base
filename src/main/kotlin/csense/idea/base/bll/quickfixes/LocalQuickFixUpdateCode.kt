@file:Suppress("unused")

package csense.idea.base.bll.quickfixes

import com.intellij.openapi.project.*
import com.intellij.psi.*
import com.intellij.psi.codeStyle.*
import org.jetbrains.kotlin.idea.util.application.*
import org.jetbrains.kotlin.psi.*


abstract class LocalQuickFixUpdateCode<T : KtElement>(
    element: T
) : LocalQuickFixOnSingleKtElement<T>(element) {

    @Suppress("ActionIsNotPreviewFriendly")
    val factory: KtPsiFactory by lazy {
        KtPsiFactory(project, markGenerated = false)
    }

    final override fun invoke(project: Project, file: PsiFile, element: T) {
        if (!element.isWritable) {
            return
        }
        val toReformat: PsiElement = project.executeWriteCommand(
            name = this::class.simpleName ?: name,
            groupId = familyName
        ) {
            tryUpdate(
                project = project,
                file = file,
                element = element
            )
        } ?: element

        reformat(project = project, element = toReformat)
    }

    abstract fun tryUpdate(project: Project, file: PsiFile, element: T): PsiElement?

    fun reformat(project: Project, element: PsiElement): PsiElement {
        val styleManager: CodeStyleManager = CodeStyleManager.getInstance(project)
        return styleManager.reformat(/* element = */ element)
    }

}