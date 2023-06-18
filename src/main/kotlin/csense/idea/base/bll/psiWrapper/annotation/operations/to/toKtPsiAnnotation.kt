package csense.idea.base.bll.psiWrapper.annotation.operations.to

import com.intellij.psi.PsiAnnotation
import csense.idea.base.bll.psiWrapper.annotation.*
import org.jetbrains.kotlin.psi.KtAnnotationEntry

fun KtAnnotationEntry.toKtPsiAnnotation(): KtPsiAnnotation.Kt {
    return KtPsiAnnotation.Kt(this)
}

fun PsiAnnotation.toKtPsiAnnotation(): KtPsiAnnotation.Psi {
    return KtPsiAnnotation.Psi(this)
}

