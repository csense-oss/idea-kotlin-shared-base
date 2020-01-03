package csense.idea.base.bll.kotlin

import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.psi.psiUtil.isPlainWithEscapes

/**
 * Simple heuristic / not fully done.
 * @receiver KtExpression
 * @return Boolean
 */
fun KtExpression.isConstant(): Boolean = when (this) {
    is KtConstantExpression -> {
        true
    }
    is KtStringTemplateExpression -> {
        this.isConstant()
    }
    is KtBlockExpression -> {
        children.size == 1 && (children[0] as KtExpression).isConstant()
    }
    is KtReturnExpression -> {
        children.size == 1 && (children[0] as KtExpression).isConstant()
    }
    else -> false
}

fun KtStringTemplateExpression.isConstant(): Boolean =
    isPlainWithEscapes()

