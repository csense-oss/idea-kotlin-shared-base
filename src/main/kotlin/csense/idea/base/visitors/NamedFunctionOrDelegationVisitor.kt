@file:Suppress("unused")

package csense.idea.base.visitors

import org.jetbrains.kotlin.psi.*


class NamedFunctionOrDelegationVisitor(
    private val onFunctionNamed: (KtNamedFunction) -> Unit,
    private val onPropertyDelegate: (KtPropertyDelegate) -> Unit
) : KtVisitorVoid() {
    override fun visitNamedFunction(function: KtNamedFunction) {
        super.visitNamedFunction(function)
        onFunctionNamed(function)
    }

    override fun visitPropertyDelegate(delegate: KtPropertyDelegate) {
        super.visitPropertyDelegate(delegate)
        onPropertyDelegate(delegate)
    }
}
