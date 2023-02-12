package csense.idea.base.bll.psiWrapper.`class`.operations

import com.intellij.openapi.project.*
import com.intellij.psi.*
import com.intellij.psi.search.*
import csense.idea.base.bll.psiWrapper.`class`.*
import org.jetbrains.kotlin.idea.stubindex.*


private val resolveMap: MutableMap<Project, MutableMap<String, KtPsiClass>> = mutableMapOf()

fun KtPsiClass.Companion.resolve(
    fqName: String,
    project: Project,
    useKotlin: Boolean = true
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

fun KtPsiClass.Companion.resolveByKotlin(fqName: String, project: Project): KtPsiClass? {
    //!??! mpp, expected actual etc!?
    return KotlinFullClassNameIndex.getInstance().get(
        /* fqName = */ fqName,
        /* project = */ project,
        /* scope = */  GlobalSearchScope.allScope(project)
    ).firstNotNullOfOrNull { it.asKtOrPsiClass() }
}

val KtPsiClass.Companion.kotlinThrowableFqName: String
    get() = "kotlin.Throwable"

val KtPsiClass.Companion.javaThrowableFqName: String
    get() = "java.lang.Throwable"

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

