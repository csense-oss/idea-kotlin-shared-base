@file:Suppress("unused")

package csense.idea.base.bll.annotator

import com.intellij.lang.annotation.*
import com.intellij.psi.*
import csense.idea.base.csense.*

abstract class TypedAnnotator<T : PsiElement> : Annotator {

    abstract val tType: Class<T>

    final override fun annotate(
        element: PsiElement,
        holder: AnnotationHolder
    ) {
        val typedElement: T = tType.tryCast(element) ?: return
        annotateTyped(typedElement = typedElement, holder = holder)
    }

    abstract fun annotateTyped(
        typedElement: T,
        holder: AnnotationHolder
    )
}