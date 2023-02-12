@file:Suppress("unused")

package csense.idea.base.visitors

import org.jetbrains.kotlin.psi.*

fun constructorVisitor(
    block: (KtConstructor<*>) -> Unit
): KtVisitorVoid = classOrObjectVisitor { it: KtClassOrObject ->
    val constructors: List<KtConstructor<*>> = listOfNotNull(it.primaryConstructor) + it.secondaryConstructors
    constructors.forEach(block)
}