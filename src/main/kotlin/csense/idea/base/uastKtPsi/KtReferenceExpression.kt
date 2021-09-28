package csense.idea.base.uastKtPsi

import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.idea.references.resolveMainReferenceToDescriptors
import org.jetbrains.kotlin.js.resolve.diagnostics.findPsi
import org.jetbrains.kotlin.psi.KtReferenceExpression
import org.jetbrains.kotlin.resolve.calls.callUtil.getCalleeExpressionIfAny
import org.jetbrains.uast.UResolvable
import org.jetbrains.uast.toUElement

@Deprecated("consider not using this, as it might be wrong.", level = DeprecationLevel.WARNING)
fun KtReferenceExpression.resolvePsi(): PsiElement? {
    return (this.toUElement() as? UResolvable)?.resolve()
        ?: getCalleeExpressionIfAny()?.resolveMainReferenceToDescriptors()?.firstOrNull()?.findPsi() ?: return null
}