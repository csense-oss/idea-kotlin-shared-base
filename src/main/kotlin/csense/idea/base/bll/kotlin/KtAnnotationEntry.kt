@file:Suppress("unused", "CANDIDATE_CHOSEN_USING_OVERLOAD_RESOLUTION_BY_LAMBDA_ANNOTATION")

package csense.idea.base.bll.kotlin

import com.intellij.openapi.project.*
import csense.idea.base.bll.psiWrapper.`class`.*
import csense.idea.base.bll.psiWrapper.`class`.operations.*
import csense.idea.base.csense.*
import org.jetbrains.kotlin.psi.*


fun List<KtAnnotationEntry>.filterThrowsAnnotation(): KtAnnotationEntry? = firstOrNull { it: KtAnnotationEntry ->
    it.isThrowsAnnotation()
}

fun KtAnnotationEntry.isThrowsAnnotation(): Boolean {
    return fqName() in setOf("kotlin.Throws", "kotlin.jvm.Throws") || shortName?.asString() == "@Throws"
}

fun KtAnnotationEntry.isDeprecatedAnnotation(): Boolean {
    return fqName() == "kotlin.Deprecated"
}

fun List<KtAnnotationEntry>.filterByFqName(fqName: String): List<KtAnnotationEntry> = filter { it: KtAnnotationEntry ->
    it.fqName() == fqName
}

fun List<KtAnnotationEntry>.anyByFqName(fqName: String): Boolean = any { it: KtAnnotationEntry ->
    it.fqName() == fqName
}

fun List<KtAnnotationEntry>.resolveAsThrowTypesOrThrowable(
    project: Project
): List<KtPsiClass> = resolveAsKClassTypes().onEmpty {
    listOfNotNull(
        KtPsiClass.getKotlinThrowable(project)
    )
}

fun List<KtAnnotationEntry>.resolveAsKClassTypes(): List<KtPsiClass> = map { it: KtAnnotationEntry ->
    it.resolveValueParametersAsKClassTypes()
}.flatten()

fun KtAnnotationEntry.resolveValueParametersAsKClassTypes(): List<KtPsiClass> =
    valueArguments.resolveAsKClassTypes()


fun KtAnnotationEntry.fqName(): String? {
    return resolveFirstClassType2()?.fqName
}

fun List<KtAnnotationEntry>.anyByFqNames(fqNames: Set<String>): Boolean = any { it: KtAnnotationEntry ->
    it.fqName() in fqNames
}

fun List<KtAnnotationEntry>.filterByFqNames(fqNames: Set<String>): List<KtAnnotationEntry> =
    filter { it: KtAnnotationEntry ->
        it.fqName() in fqNames
    }