package csense.idea.base.bll.psiWrapper.`class`.operations

import com.intellij.openapi.project.*
import com.intellij.psi.*
import com.intellij.psi.search.GlobalSearchScope
import csense.idea.base.bll.psiWrapper.`class`.*
import csense.idea.base.wrapper.KotlinClassIndexWrapperStrategy
import org.jetbrains.kotlin.idea.stubindex.KotlinFullClassNameIndex
import org.jetbrains.kotlin.name.FqName
import kotlin.reflect.full.staticFunctions


private val resolveMap: MutableMap<Project, MutableMap<String, KtPsiClass>> = mutableMapOf()

fun KtPsiClass.Companion.resolve(
    fqName: String,
    project: Project,
    useKotlin: Boolean = true,
): KtPsiClass? = when (useKotlin) {
    true -> resolveByKotlin(fqName = fqName, project = project)
    false -> resolveByJava(fqName = fqName, project = project)
}


fun KtPsiClass.Companion.resolveByJava(fqName: String, project: Project): KtPsiClass? {
    return JavaPsiFacade.getInstance(project).findClass(
        /* p0 = */ fqName,
        /* p1 = */ GlobalSearchScope.allScope(project)
    )?.asKtOrPsiClass()
}

fun KtPsiClass.Companion.resolveByKotlin(
    fqName: String,
    project: Project
): KtPsiClass? {
    return resolveClass(FqName(fqName), project)?.firstOrNull()
}


private fun resolveClass(fqName: FqName, project: Project): List<KtPsiClass>? {
    return KotlinFullClassNameIndexWrapper().resolveClass(
        fqName = fqName.asString(),
        project = project,
        globalSearchScope = GlobalSearchScope.allScope(project)
    )
}

//
//private fun resolveFqName(
//    fqName: FqName,
//    project: Project,
//    contextElement: PsiElement?
//): PsiElement? {
//    if (fqName.isRoot) return null
//    return constructImportDirectiveWithContext(fqName, project, contextElement)
//        .getChildOfType<KtDotQualifiedExpression>()
//        ?.selectorExpression
//        ?.references
//        ?.firstNotNullOfOrNull(PsiReference::resolve)
//}
//
//private fun constructImportDirectiveWithContext(
//    fqName: FqName,
//    project: Project,
//    contextElement: PsiElement?
//): KtImportDirective {
//    val importDirective = KtPsiFactory(project).createImportDirective(ImportPath(fqName, false))
//    importDirective.containingKtFile.analysisContext = contextElement?.containingFile
//    return importDirective
//}
//
////private fun resolveFqNameOfJavaClassByIndex(fqName: FqName, project: Project): PsiClass? {
////    val scope = GlobalSearchScope.allScope(project)
////    val fqNameString = fqName.asString()
////    return JavaFullClassNameIndex.getInstance().getClasses(fqNameString, project, scope)
////        .firstOrNull {
////            it.qualifiedName == fqNameString
////        }
////}
//

val KtPsiClass.Companion.kotlinThrowableFqName: String
    get() = "kotlin.Throwable"

val KtPsiClass.Companion.kotlinRuntimeExceptionFqName: String
    get() = "kotlin.RuntimeException"

val KtPsiClass.Companion.javaThrowableFqName: String
    get() = "java.lang.Throwable"

val KtPsiClass.Companion.javaRuntimeExceptionFqName: String
    get() = "java.lang.RuntimeException"

fun KtPsiClass.Companion.getKotlinThrowable(project: Project): KtPsiClass? {
    val map: MutableMap<String, KtPsiClass> = resolveMap.getOrPut(project, defaultValue = ::mutableMapOf)
    return map.getOrPut(kotlinThrowableFqName) {
        resolve(
            fqName = kotlinThrowableFqName,
            project = project,
            useKotlin = true
        ) ?: return@getKotlinThrowable null
    }
}

fun KtPsiClass.Companion.getJavaThrowable(project: Project): KtPsiClass? {
    val map: MutableMap<String, KtPsiClass> = resolveMap.getOrPut(project, defaultValue = ::mutableMapOf)
    return map.getOrPut(javaThrowableFqName) {
        resolve(
            fqName = javaThrowableFqName,
            project = project,
            useKotlin = false
        ) ?: return@getJavaThrowable null
    }
}


class KotlinFullClassNameIndexWrapper {

    private val strategy: KotlinClassIndexWrapperStrategy

    init {
        strategy = when {
            isPreIdea2024() -> KotlinClassIndexWrapperStrategy.PreIdea2024()
            else -> KotlinClassIndexWrapperStrategy.PostIdea2024()
        }
    }

    fun resolveClass(
        fqName: String,
        project: Project,
        globalSearchScope: GlobalSearchScope,
    ): List<KtPsiClass> {
        return strategy.resolveClassAndAlias(
            fqName = fqName,
            project = project,
            globalSearchScope = globalSearchScope
        )
    }

    private fun isPreIdea2024(): Boolean {
        return KotlinFullClassNameIndex::class.staticFunctions.any { it.name == "getInstance" }
    }
}