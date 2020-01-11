package csense.idea.base.bll.kotlin

import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.psi.KtBlockExpression

/**
 * Adds the given element as the first element inside of this block (so not outside the scope)
 * @receiver KtBlockExpression
 * @param element PsiElement
 * @return (com.intellij.psi.PsiElement..com.intellij.psi.PsiElement?)
 */
fun KtBlockExpression.addFirstInScope(element: PsiElement) = addAfter(element, lBrace)
/**
 * Adds the given element as the last element inside of this block (so not outside the scope)
 * @receiver KtBlockExpression
 * @param element PsiElement
 * @return (com.intellij.psi.PsiElement..com.intellij.psi.PsiElement?)
 */
fun KtBlockExpression.addLastInScope(element: PsiElement) = addBefore(element, rBrace)
