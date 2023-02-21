@file:Suppress("unused")

package csense.idea.base.bll.kotlin

import com.intellij.openapi.project.*
import csense.idea.base.bll.psiWrapper.`class`.*
import org.jetbrains.kotlin.psi.*

fun KtAnnotated.throwsAnnotationOrNull(): KtAnnotationEntry? =
    annotationEntries.filterThrowsAnnotation()

fun KtAnnotated.throwsTypes(): List<KtPsiClass> {
    val annotations: KtAnnotationEntry = throwsAnnotationOrNull() ?: return emptyList()
    return listOf(annotations).resolveAsThrowTypes(project)
}
