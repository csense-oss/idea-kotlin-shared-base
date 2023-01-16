package csense.idea.base.bll.kotlin

import org.jetbrains.kotlin.idea.core.*
import org.jetbrains.kotlin.idea.refactoring.*
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.psi.psiUtil.*


fun KtProperty.hasCustomSetterGetter(): Boolean {
    return getter != null || setter != null
}

fun KtProperty.hasConstantCustomGetterOnly(): Boolean {
    return getter != null && setter == null && isGetterConstant()
}

fun KtProperty.isGetterConstant(): Boolean {
    val exp = getterBody
        ?: return isVal && initializer?.isConstant() == true //if no custom getter then if it is a val it will be constant.
    return exp.isConstant()
}

val KtProperty.getterBody: KtExpression?
    get() = getter?.getChildOfType()


fun KtProperty.isOverriding(): Boolean = modifierList?.isOverriding() ?: false

fun KtProperty.isAbstractOrOpen(): Boolean = isAbstract() || isOverridable()


inline val KtProperty.isVal: Boolean
    get() = !isVar

fun KtProperty.initalizerOrGetter(): KtExpression? =
    initializer ?: getterBody