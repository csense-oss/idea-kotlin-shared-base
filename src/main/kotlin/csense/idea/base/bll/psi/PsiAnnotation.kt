package csense.idea.base.bll.psi

import com.intellij.psi.*

fun PsiAnnotation.isThrowsAnnotation(): Boolean {
    return qualifiedName == "Throws"
}