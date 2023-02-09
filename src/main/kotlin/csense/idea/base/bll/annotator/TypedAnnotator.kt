@file:Suppress("unused")

package csense.idea.base.bll.annotator

import com.intellij.lang.annotation.*
import com.intellij.psi.*
import csense.kotlin.extensions.*
import kotlin.contracts.*

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

//TODO use from csense 0.1.0
@Suppress("UNCHECKED_CAST")
fun <T> Class<T>.tryCast(other: Any): T? = when {
    other::class.java.isAssignableFrom(this) -> other as T
    else -> null
}