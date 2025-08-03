@file:Suppress("unused")

package csense.idea.base.bll.kotlin

import csense.idea.base.bll.psiWrapper.`class`.*
import csense.idea.base.bll.psiWrapper.`class`.operations.*
import org.jetbrains.kotlin.kdoc.psi.api.*
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.psi.psiUtil.*

fun KtAnnotated.throwsAnnotationOrNull(): KtAnnotationEntry? =
    annotationEntries.filterThrowsAnnotation()

fun KtAnnotated.throwsTypes(): List<KtPsiClass> {
    val fromDocumentation: List<KtPsiClass> = throwsDocumentation()
    val fromAnnotations: List<KtPsiClass> = throwsAnntationTypes()
    return fromDocumentation + fromAnnotations
}

fun KtAnnotated.throwsAnntationTypes(): List<KtPsiClass> {
    val annotations: KtAnnotationEntry = throwsAnnotationOrNull() ?: return emptyList()
    return listOf(annotations).resolveAsThrowTypesOrThrowable(project)
}

fun KtAnnotated.throwsDocumentation(): List<KtPsiClass> {
    if(containingKtFile.isStubbed()){
        //stubs does not have comments
        return emptyList()
    }
    val docsThrows = "@throws"
    val kdocs: List<KDoc> = collectDescendantsOfType()
    val kdocsText: List<String> = kdocs.map { it: KDoc ->
        it.text
    }
    val throwsDocsText: List<String> = kdocsText.filter { it.contains(docsThrows) }
    val classNames: List<String> = throwsDocsText.map { it: String -> //todo this is kinda sub optimal (kinda sus)
        it.substringAfter(docsThrows).trim().substringBefore(" ").trim()
    }
    //TODO kotlin in front? hmmmmmmmmm
    return classNames.mapNotNull { className: String ->
        KtPsiClass.resolve(fqName = className, project = project) ?: KtPsiClass.resolve(
            fqName = "kotlin.$className",
            project = project
        )
    }
}

fun KtAnnotated.hasAnnotationBy(predicate: (KtAnnotationEntry) -> Boolean): Boolean {
    return annotationEntries.any { it: KtAnnotationEntry ->
        predicate(it)
    }
}

fun KtAnnotated.hasAnnotationClass(predicate: (KtPsiClass) -> Boolean): Boolean {
    return hasAnnotationBy { it: KtAnnotationEntry ->
        val resolved: KtPsiClass = it.resolveFirstClassType2() ?: return@hasAnnotationBy false
        predicate(resolved)
    }
}