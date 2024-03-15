@file:Suppress("unused")

package csense.idea.base.bll.psiWrapper.`class`.operations.has

import com.intellij.psi.*
import csense.idea.base.bll.kotlin.*
import csense.idea.base.bll.psiWrapper.annotation.*
import csense.idea.base.bll.psiWrapper.annotation.operations.to.*
import csense.idea.base.bll.psiWrapper.`class`.*
import org.jetbrains.kotlin.psi.*


fun KtPsiClass.hasAnnotationBy(predicate: (KtPsiAnnotation) -> Boolean): Boolean = when (this) {
    is KtPsiClass.Kt -> hasAnnotationBy(predicate)
    is KtPsiClass.Psi -> hasAnnotationBy(predicate)
}

fun KtPsiClass.Kt.hasAnnotationBy(predicate: (KtPsiAnnotation) -> Boolean): Boolean {
    return ktClassOrObject.hasAnnotationBy { it: KtAnnotationEntry ->
        predicate(it.toKtPsiAnnotation())
    }
}

fun KtPsiClass.Psi.hasAnnotationBy(predicate: (KtPsiAnnotation) -> Boolean): Boolean {
    return psiClass.annotations.any { it: PsiAnnotation ->
        predicate(it.toKtPsiAnnotation())
    }
}
