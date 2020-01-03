package csense.idea.base.annotationss

import com.intellij.psi.PsiModifierListOwner
import org.jetbrains.kotlin.psi.KtAnnotated
import org.jetbrains.uast.UAnnotation
import org.jetbrains.uast.toUElementOfType


fun KtAnnotated.uAnnotaions(): List<UAnnotation> {
    return annotationEntries.mapNotNull { it.toUElementOfType<UAnnotation>() }
}

fun PsiModifierListOwner.uAnnotations(): List<UAnnotation> {
    return annotations.mapNotNull { it.toUElementOfType<UAnnotation>() }
}