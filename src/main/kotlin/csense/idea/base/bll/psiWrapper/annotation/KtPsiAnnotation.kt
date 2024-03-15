package csense.idea.base.bll.psiWrapper.annotation

import com.intellij.psi.PsiAnnotation
import csense.idea.base.bll.kotlin.*
import org.jetbrains.kotlin.psi.KtAnnotationEntry

sealed interface KtPsiAnnotation {

    val fqName: String?

    public class Kt(val annotation: KtAnnotationEntry) : KtPsiAnnotation {
        override val fqName: String? = annotation.fqName()
    }

    public class Psi(val annotation: PsiAnnotation) : KtPsiAnnotation {
        override val fqName: String? = annotation.qualifiedName
    }

}

