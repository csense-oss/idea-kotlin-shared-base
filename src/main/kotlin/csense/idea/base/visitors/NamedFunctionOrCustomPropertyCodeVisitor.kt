@file:Suppress("unused")

package csense.idea.base.visitors

import csense.idea.base.bll.kotlin.*
import org.jetbrains.kotlin.psi.*


class NamedFunctionOrCustomPropertyCodeVisitor(
    private val onFunctionNamed: (KtNamedFunction) -> Unit,
    private val onPropertyWithInnerCode: (KtProperty) -> Unit
) : KtVisitorVoid() {
    override fun visitNamedFunction(function: KtNamedFunction) {
        super.visitNamedFunction(function)
        onFunctionNamed(function)
    }

    override fun visitProperty(property: KtProperty) {
        super.visitProperty(property)
        if (property.hasCustomCode()) {
            onPropertyWithInnerCode(property)
        }
    }
}
