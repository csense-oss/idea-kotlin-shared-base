@file:Suppress("unused")

package csense.idea.base.bll.psiWrapper.function.operations

import com.intellij.lang.jvm.*
import com.intellij.openapi.project.*
import com.intellij.psi.*
import csense.idea.base.bll.kotlin.*
import csense.idea.base.bll.psi.*
import csense.idea.base.bll.psiWrapper.`class`.*
import csense.idea.base.bll.psiWrapper.`class`.operations.*
import csense.idea.base.bll.psiWrapper.function.*
import csense.kotlin.extensions.collections.*
import org.jetbrains.kotlin.psi.*


fun KtPsiFunction.throwsTypes(): List<KtPsiClass> = when (this) {
    is KtPsiFunction.Kt -> function.throwsTypes()
    is KtPsiFunction.Psi -> function.throwsTypes()
}

fun PsiMethod.throwsTypes(): List<KtPsiClass> {
    val throws: List<PsiAnnotation> = annotations.filter { it.isThrowsAnnotation() }
    if (throws.isEmpty()) {
        return emptyList()
    }
    //TODO
    //TODO varargs!?... :(
//    val result: List<KtPsiClass> = throws.map { it: PsiAnnotation ->
//        parameters.mapNotNull { annotationParameter: JvmParameter ->
//            annotationParameter.type
//        }
//        it.resolveFirstClassType2()
//    }
//    if (result.isNotEmpty()) {
//        return result
//    }
    return listOfNotNull(KtPsiClass.getJavaThrowable(project))
}
