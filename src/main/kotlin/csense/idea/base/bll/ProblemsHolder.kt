@file:Suppress("unused")

package csense.idea.base.bll

import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.psi.KtCallExpression
import org.jetbrains.kotlin.psi.stubs.elements.KtNameReferenceExpressionElementType


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