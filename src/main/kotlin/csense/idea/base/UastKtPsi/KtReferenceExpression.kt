package csense.idea.base.UastKtPsi

import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.idea.references.resolveMainReferenceToDescriptors
import org.jetbrains.kotlin.js.resolve.diagnostics.findPsi
import org.jetbrains.kotlin.psi.KtCallExpression
import org.jetbrains.kotlin.resolve.calls.callUtil.getCalleeExpressionIfAny
import org.jetbrains.uast.UResolvable
import org.jetbrains.uast.toUElement

fun KtCallExpression.resolvePsi(): PsiElement? {
    return (this.toUElement() as? UResolvable)?.resolve()
        ?: getCalleeExpressionIfAny()?.resolveMainReferenceToDescriptors()?.firstOrNull()?.findPsi() ?: return null
}
