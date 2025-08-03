package csense.idea.base.bll.psi

import com.intellij.psi.*
import org.jetbrains.kotlin.psi.*

fun PsiNamedElement.isInterfaceClass(): Boolean = when (this) {
    is KtClass -> isInterface()
    is PsiClass -> isInterface
    else -> false
}