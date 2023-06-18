@file:Suppress("unused")

package csense.idea.base.bll.psiWrapper.function.operations

import com.intellij.psi.*
import csense.idea.base.bll.kotlin.*
import csense.idea.base.bll.psiWrapper.`class`.*
import csense.idea.base.bll.psiWrapper.`class`.operations.*
import csense.idea.base.bll.psiWrapper.`class`.operations.to.*
import csense.idea.base.bll.psiWrapper.function.*


fun KtPsiFunction.throwsTypes(): List<KtPsiClass> = when (this) {
    is KtPsiFunction.Kt -> function.throwsTypes()
    is KtPsiFunction.Psi -> function.throwsTypes()
}

fun PsiMethod.throwsTypes(): List<KtPsiClass> {
    val throws: Array<PsiClassType> = throwsList.referencedTypes
    if (throws.isEmpty()) {
        return emptyList()
    }
    val throwable: KtPsiClass? = KtPsiClass.getJavaThrowable(project)
    return throws.mapNotNull { classType: PsiClassType ->
        val resolvedClass: PsiClass = classType.resolve() ?: return@mapNotNull throwable
        resolvedClass.toKtPsiClass()
    }
}

