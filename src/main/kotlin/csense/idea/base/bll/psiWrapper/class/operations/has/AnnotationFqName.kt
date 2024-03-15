@file:Suppress("unused")

package csense.idea.base.bll.psiWrapper.`class`.operations.has

import csense.idea.base.bll.kotlin.*
import csense.idea.base.bll.psiWrapper.`class`.*
import org.jetbrains.kotlin.psi.*


fun KtPsiClass.hasAnnotationBy(fqName: String): Boolean = when (this) {
    is KtPsiClass.Kt -> hasAnnotationBy(fqName)
    is KtPsiClass.Psi -> hasAnnotationBy(fqName)
}

fun KtPsiClass.Kt.hasAnnotationBy(fqName: String): Boolean {
    return ktClassOrObject.hasAnnotationBy { it: KtAnnotationEntry ->
        fqName == it.fqName()
    }
}

fun KtPsiClass.Psi.hasAnnotationBy(fqName: String): Boolean {
    return psiClass.hasAnnotation(fqName)
}
