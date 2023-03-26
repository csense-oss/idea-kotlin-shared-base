@file:Suppress("unused")

package csense.idea.base.bll.kotlin

import csense.idea.base.bll.psiWrapper.`class`.*
import csense.idea.base.bll.psiWrapper.function.operations.*
import org.jetbrains.kotlin.idea.core.*
import org.jetbrains.kotlin.idea.refactoring.*
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.psi.psiUtil.*


fun KtProperty.hasCustomCode(): Boolean{
    return hasDelegate() || hasCustomSetterGetter()
}

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


fun KtProperty.annotationEntriesWithGetterAnnotations(): List<KtAnnotationEntry> {
    val getterAnnotations: List<KtAnnotationEntry> = getter?.annotationEntries.orEmpty()
    return annotationEntries + getterAnnotations
}

fun KtProperty.annotationEntriesWithSetterAnnotations(): List<KtAnnotationEntry> {
    val getterAnnotations: List<KtAnnotationEntry> = setter?.annotationEntries.orEmpty()
    return annotationEntries + getterAnnotations
}

fun KtProperty.throwsTypesWithGetter(): List<KtPsiClass> {
    return listOfNotNull(annotationEntriesWithGetterAnnotations().filterThrowsAnnotation()).resolveAsKClassTypes()
}

fun KtProperty.throwsTypesWithSetter(): List<KtPsiClass> {
    return listOfNotNull(annotationEntriesWithSetterAnnotations().filterThrowsAnnotation()).resolveAsKClassTypes()
}