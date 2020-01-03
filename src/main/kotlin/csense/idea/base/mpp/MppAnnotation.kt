package csense.idea.base.mpp

import com.intellij.codeInsight.ExternalAnnotationsManager
import com.intellij.psi.PsiAnnotation
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiModifierListOwner
import org.jetbrains.kotlin.asJava.classes.KtLightClass
import org.jetbrains.kotlin.asJava.elements.KtLightMethod
import org.jetbrains.kotlin.asJava.toLightAnnotation
import org.jetbrains.kotlin.psi.*

/**
 * Shared concept of a MPP capable annotation (UAST does not work for MPP projects.)
 */
data class MppAnnotation(val qualifiedName: String)

fun PsiAnnotation.toMppAnnotation(): MppAnnotation? {
    val qualifiedName = qualifiedName ?: return null
    return MppAnnotation(qualifiedName)
}

fun KtAnnotation.toMppAnnotation(): MppAnnotation? {
    val asLight = toLightAnnotation()
    val qualifiedName = asLight?.qualifiedName ?: return null
    return MppAnnotation(qualifiedName)
}

fun KtAnnotationEntry.toMppAnnotation(): MppAnnotation? {
    //TODO make me..
    val asLight = toLightAnnotation()

    val qualifiedName = asLight?.qualifiedName ?: return null
    return MppAnnotation(qualifiedName)
}

fun PsiElement.getItemMppAnnotations(): List<MppAnnotation> = when (this) {
    is KtLightMethod -> annotations.toMppAnnotations()
    is KtFunction -> annotationEntries.toMppAnnotations()
    is PsiMethod -> annotations.toMppAnnotations()
    else -> emptyList()
}

fun List<KtAnnotationEntry>.toMppAnnotations():List<MppAnnotation> = mapNotNull { it.toMppAnnotation() }
fun Array<PsiAnnotation>.toMppAnnotations(): List<MppAnnotation> = mapNotNull { it.toMppAnnotation() }





fun PsiElement.resolveAllClassMppAnnotation(externalAnnotationsManager: ExternalAnnotationsManager? = null): List<MppAnnotation> {
    val extManager = externalAnnotationsManager ?: ExternalAnnotationsManager.getInstance(project)
    val internal = when (this) {
        is KtLightClass -> annotations.mapNotNull { it.toMppAnnotation() }
        is KtClass -> annotationEntries.mapNotNull { it.toMppAnnotation() }
        is KtClassOrObject -> annotationEntries.mapNotNull { it.toMppAnnotation() }
        else -> emptyList()
    }
    val external = if (this is PsiModifierListOwner) {
        extManager.findExternalAnnotations(this)?.mapNotNull {
            it.toMppAnnotation()
        } ?: emptyList()
    } else {
        emptyList()
    }
    return internal + external
}


fun PsiElement.resolveAllMethodAnnotationMppAnnotation(externalAnnotationsManager: ExternalAnnotationsManager? = null): List<MppAnnotation> {
    val extManager = externalAnnotationsManager ?: ExternalAnnotationsManager.getInstance(project)
    val ownAnnotations = this.getItemMppAnnotations()
    val external = (this as? PsiModifierListOwner)?.let {
        extManager.findExternalAnnotations(it)?.toMppAnnotations()
    } ?: emptyList()
    return ownAnnotations + external
}
