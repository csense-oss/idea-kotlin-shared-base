package csense.idea.base.bll.psiWrapper.function.operations

import com.intellij.psi.PsiAnnotation
import csense.idea.base.bll.psiWrapper.annotation.*
import csense.idea.base.bll.psiWrapper.function.*
import org.jetbrains.kotlin.psi.*

fun KtPsiFunction.containsAnnotationBy(fqName: String): Boolean {
    return containsAnnotationBy { it: KtPsiAnnotation ->
        it.fqName == fqName
    }
}

fun KtPsiFunction.containsAnnotationBy(
    predicate: (KtPsiAnnotation) -> Boolean
): Boolean = when (this) {
    is KtPsiFunction.Kt -> containsAnnotationBy(predicate)
    is KtPsiFunction.Psi -> containsAnnotationBy(predicate)
}

fun KtPsiFunction.Kt.containsAnnotationBy(
    predicate: (KtPsiAnnotation) -> Boolean
): Boolean {
    return function.annotationEntries.any { it: KtAnnotationEntry ->
        predicate(KtPsiAnnotation.Kt(it))
    }
}

fun KtPsiFunction.Psi.containsAnnotationBy(
    predicate: (KtPsiAnnotation) -> Boolean
): Boolean {
    return function.annotations.any { it: PsiAnnotation ->
        predicate(KtPsiAnnotation.Psi(it))
    }
}

fun KtPsiFunction.containsAnyAnnotation(
    fqNames: Set<String>
): Boolean {
    return containsAnnotationBy { it: KtPsiAnnotation ->
        it.fqName in fqNames
    }
}