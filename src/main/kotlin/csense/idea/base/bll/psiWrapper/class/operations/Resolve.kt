package csense.idea.base.bll.psiWrapper.`class`.operations

import com.intellij.openapi.project.*
import com.intellij.psi.*
import com.intellij.psi.search.*
import csense.idea.base.bll.psiWrapper.`class`.*
import csense.idea.base.wrapper.*
import org.jetbrains.kotlin.name.*
import kotlin.reflect.full.*


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
    return KotlinFullClassNameIndexWrapper.resolveClass(
        fqName = fqName.asString(),
        project = project,
        globalSearchScope = GlobalSearchScope.allScope(project)
    )
}

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


object KotlinFullClassNameIndexWrapper {

    private val strategy: KotlinClassIndexWrapperStrategy

    init {
        strategy = when {
            isPreIdea2024() -> KotlinClassIndexWrapperStrategy.PreIdea2024
            else -> KotlinClassIndexWrapperStrategy.PostIdea2024
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
            scope = globalSearchScope
        )
    }

    private fun isPreIdea2024(): Boolean {
        return try {
            Class.forName("org.jetbrains.kotlin.idea.stubindex.KotlinFullClassNameIndex").kotlin.functions.any { it.name == "getInstance" }
        } catch (exception: ClassNotFoundException) {
            false
        }
    }
}