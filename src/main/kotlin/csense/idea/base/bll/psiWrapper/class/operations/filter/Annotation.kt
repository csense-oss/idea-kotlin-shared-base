@file:Suppress("unused")

package csense.idea.base.bll.psiWrapper.`class`.operations.filter

import com.intellij.psi.*
import csense.idea.base.bll.psiWrapper.annotation.*
import csense.idea.base.bll.psiWrapper.annotation.operations.to.*
import csense.idea.base.bll.psiWrapper.`class`.*
import csense.idea.base.csense.*
import org.jetbrains.kotlin.psi.*


fun KtPsiClass.filterAnnotationsBy(predicate: (KtPsiAnnotation) -> Boolean): List<KtPsiAnnotation> = when (this) {
    is KtPsiClass.Kt -> filterAnnotationsBy(predicate)
    is KtPsiClass.Psi -> filterAnnotationsBy(predicate)
}

fun KtPsiClass.Kt.filterAnnotationsBy(predicate: (KtPsiAnnotation) -> Boolean): List<KtPsiAnnotation> {
    return ktClassOrObject.annotationEntries.filterMapped(
        predicate = predicate,
        transform = { it: KtAnnotationEntry ->
            it.toKtPsiAnnotation()
        }
    )
}

fun KtPsiClass.Psi.filterAnnotationsBy(predicate: (KtPsiAnnotation) -> Boolean): List<KtPsiAnnotation> {
    return psiClass.annotations.filterMapped(
        predicate = predicate,
        transform = { it: PsiAnnotation ->
            it.toKtPsiAnnotation()
        }
    )
}
