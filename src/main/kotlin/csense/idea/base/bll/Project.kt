package csense.idea.base.bll

import com.intellij.openapi.project.Project
import com.intellij.psi.JavaPsiFacade
import com.intellij.psi.search.GlobalSearchScope
import org.jetbrains.uast.UClass
import org.jetbrains.uast.toUElementOfType


fun Project.getJavaLangThrowableUClass(): UClass? {
    return JavaPsiFacade.getInstance(this)
        .findClass("java.lang.Throwable", GlobalSearchScope.allScope(this))
        ?.toUElementOfType<UClass>()
}