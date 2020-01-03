package csense.idea.base.bll

import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import csense.kotlin.extensions.toUnit
import csense.kotlin.extensions.tryAndLog


/**
 * since register problem can throw if the types are "error" psi types...
 * @receiver ProblemsHolder
 * @param psiElement PsiElement
 * @param descriptionTemplate String
 */
fun ProblemsHolder.registerProblemSafe(psiElement: PsiElement, descriptionTemplate: String) = tryAndLog {
    registerProblem(psiElement, descriptionTemplate)
}.toUnit()

/**
 * since register problem can throw if the types are "error" psi types...
 * @receiver ProblemsHolder
 * @param psiElement PsiElement
 * @param descriptionTemplate String
 * @param fixes Array<out LocalQuickFix>
 */
fun ProblemsHolder.registerProblemSafe(psiElement: PsiElement, descriptionTemplate: String, vararg fixes: LocalQuickFix) = tryAndLog {
    registerProblem(psiElement, descriptionTemplate, *fixes)
}.toUnit()