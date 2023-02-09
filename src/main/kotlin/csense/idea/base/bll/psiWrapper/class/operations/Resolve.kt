package csense.idea.base.bll.psiWrapper.`class`.operations

import com.intellij.openapi.project.*
import com.intellij.psi.*
import com.intellij.psi.search.*
import csense.idea.base.bll.psiWrapper.`class`.*
import org.jetbrains.kotlin.idea.stubindex.*


fun KtPsiClass.Companion.resolve(fqName: String, project: Project, useKotlin: Boolean = true): KtPsiClass? {
    if (!useKotlin) {
        return JavaPsiFacade.getInstance(project).findClass(
            /* p0 = */ fqName,
            /* p1 = */ GlobalSearchScope.allScope(project)
        )?.asKtOrPsiClass()


    }
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

fun KtPsiClass.Companion.getKotlinThrowable(project: Project): KtPsiClass? = resolve(
    fqName = kotlinThrowableFqName,
    project = project,
    useKotlin = true
)

fun KtPsiClass.Companion.getJavaThrowable(project: Project): KtPsiClass? = resolve(
    fqName = javaThrowableFqName,
    project = project,
    useKotlin = false
)

