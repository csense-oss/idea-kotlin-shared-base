package csense.idea.base.visitors

import org.jetbrains.kotlin.psi.*

fun constructorVisitor(
    block: (KtConstructor<*>) -> Unit
): KtVisitorVoid = classOrObjectVisitor {
    val constructors = listOfNotNull(it.primaryConstructor) + it.secondaryConstructors
    constructors.forEach(block)
}