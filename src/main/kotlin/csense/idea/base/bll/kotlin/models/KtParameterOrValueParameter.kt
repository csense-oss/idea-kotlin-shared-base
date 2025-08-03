@file:Suppress("CanBeParameter")

package csense.idea.base.bll.kotlin.models

import org.jetbrains.kotlin.psi.*


sealed interface KtParameterOrValueParameter  {

    val declaration: KtNamedDeclaration

    class Property(val property: KtProperty) : KtParameterOrValueParameter {
        override val declaration: KtNamedDeclaration = property
    }

    class ValueParameter(val parameter: KtParameter) : KtParameterOrValueParameter {
        override val declaration: KtNamedDeclaration = parameter
    }
}