package csense.idea.base.bll.kotlin

import org.jetbrains.kotlin.idea.core.isOverridable
import org.jetbrains.kotlin.idea.refactoring.isAbstract
import org.jetbrains.kotlin.psi.KtExpression
import org.jetbrains.kotlin.psi.KtProperty
import org.jetbrains.kotlin.psi.psiUtil.getChildOfType


fun KtProperty.hasCustomSetterGetter(): Boolean {
    return getter != null || setter != null
}

fun KtProperty.hasConstantCustomGetterOnly(): Boolean {
    return getter != null && setter == null && isGetterConstant()
}

fun KtProperty.isGetterConstant(): Boolean {
    val exp = getterBody ?: return false
    return exp.isConstant()
}

val KtProperty.getterBody: KtExpression?
    get() = getter?.getChildOfType()


fun KtProperty.isOverriding(): Boolean = modifierList?.isOverriding() ?: false

fun KtProperty.isAbstractOrOpen(): Boolean = isAbstract() || isOverridable()

