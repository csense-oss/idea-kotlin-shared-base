@file:Suppress("unused")

package csense.idea.base.bll.kotlin

import csense.idea.base.bll.psiWrapper.`class`.*
import csense.idea.base.csense.*
import org.jetbrains.kotlin.idea.core.*
import org.jetbrains.kotlin.idea.refactoring.*
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.psi.psiUtil.*


fun KtProperty.hasCustomCode(): Boolean {
    return hasDelegate() || hasCustomSetterOrGetter()
}

fun KtProperty.hasCustomSetterOrGetter(): Boolean {
    return getter != null || setter != null
}

fun KtProperty.hasConstantCustomGetterOnly(): Boolean {
    return getter != null && setter == null && isConstantProperty()
}

fun KtProperty.isConstantProperty(): Boolean {
    getterBody.onNotNull<KtExpression, Nothing> { expression: KtExpression ->
        return@isConstantProperty expression != this && expression.isConstant()
    }
    return isVal
}

fun KtProperty.hasOnlyGetterVal(): Boolean {
    return getter != null && setter == null && isVal
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