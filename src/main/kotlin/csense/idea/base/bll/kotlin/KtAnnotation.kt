@file:Suppress("unused", "CANDIDATE_CHOSEN_USING_OVERLOAD_RESOLUTION_BY_LAMBDA_ANNOTATION")

package csense.idea.base.bll.kotlin

import com.intellij.openapi.project.Project
import csense.idea.base.*
import csense.idea.base.bll.psiWrapper.`class`.*
import csense.idea.base.bll.psiWrapper.`class`.operations.*
import csense.kotlin.extensions.collections.*
import org.jetbrains.kotlin.psi.*

fun KtAnnotationEntry.isThrowsAnnotation(): Boolean {
    return shortName?.asString() == "Throws"
}


fun List<KtAnnotationEntry>.filterThrowsAnnotations(): List<KtAnnotationEntry> = filter { it: KtAnnotationEntry ->
    it.isThrowsAnnotation()
}

fun List<KtAnnotationEntry>.resolveAsThrowTypes(
    project: Project
): List<KtPsiClass> = resolveClassTypes().onEmpty {
    listOfNotNull(
        KtPsiClass.getKotlinThrowable(project)
    )
}

fun List<KtAnnotationEntry>.resolveClassTypes(): List<KtPsiClass> = map { it: KtAnnotationEntry ->
    it.valueArguments.mapNotNull { annotation: ValueArgument ->
        //TODO. if for some cool reason the code contains "arrayOf" or alike (with star operator etc) then this will fail badly.
        annotation.getArgumentExpression()?.resolveFirstClassType2()
    }
}.flatten()