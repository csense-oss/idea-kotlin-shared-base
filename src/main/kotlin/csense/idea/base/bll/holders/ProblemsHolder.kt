@file:Suppress("unused")

package csense.idea.base.bll

import com.intellij.codeInspection.*
import com.intellij.psi.*
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.psi.stubs.elements.*


/**
 * since register problem can throw if the types are "error" psi types...
 * @receiver ProblemsHolder
 * @param psiElement PsiElement
 * @param descriptionTemplate String
 */
fun ProblemsHolder.registerProblemSafe(psiElement: PsiElement, descriptionTemplate: String) {
    registerProblem(psiElement.findValidProblemElement(), descriptionTemplate)
}

/**
 * since register problem can throw if the types are "error" psi types...
 * @receiver ProblemsHolder
 * @param psiElement PsiElement
 * @param descriptionTemplate String
 * @param fixes Array<out LocalQuickFix>
 */
fun ProblemsHolder.registerProblemSafe(
    psiElement: PsiElement,
    descriptionTemplate: String,
    vararg fixes: LocalQuickFix
) {
    registerProblem(psiElement.findValidProblemElement(), descriptionTemplate, *fixes)
}

/**
 * Finds a "better" element to use for "registerProblem";
 * since there are a lot of element types that causes assertion issues ect.
 * @receiver [PsiElement]
 * @return [PsiElement] a better alternative
 */
fun PsiElement.findValidProblemElement(): PsiElement {
    if (this is KtNameReferenceExpressionElementType) {
        return parent ?: this
    }
    return if (this is KtCallExpression) {
        calleeExpression ?: parent ?: this
    } else {
        this
    }
}


/**
 * Will report a problem and highlight the error'ed element rather han just underlining it.
 */
fun ProblemsHolder.registerProblemHighlightElement(
    psiElement: PsiElement,
    descriptionTemplate: String,
    fixes: Array<LocalQuickFix> = arrayOf()
) {
    val error: ProblemDescriptor = InspectionManager.getInstance(project).createProblemDescriptor(
        /* psiElement = */ psiElement,
        /* descriptionTemplate = */ descriptionTemplate,
        /* fixes = */ fixes,
        /* highlightType = */ ProblemHighlightType.ERROR,
        /* onTheFly = */ true,
        /* isAfterEndOfLine = */ false
    )
    registerProblem(error)
}