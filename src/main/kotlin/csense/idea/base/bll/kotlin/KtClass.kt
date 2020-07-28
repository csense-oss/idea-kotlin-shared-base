@file:Suppress("unused")

package csense.idea.base.bll.kotlin

import com.intellij.psi.PsiClass
import com.intellij.psi.search.searches.ClassInheritorsSearch
import org.jetbrains.kotlin.asJava.toLightClass
import org.jetbrains.kotlin.idea.core.isOverridable
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtEnumEntry
import org.jetbrains.kotlin.psi.psiUtil.collectDescendantsOfType
import org.jetbrains.kotlin.psi.psiUtil.isAbstract

fun KtClass.isAbstractOrOpen(): Boolean {
    return isAbstract() || isOverridable()
}

fun KtClass.getEnumValues(): List<KtEnumEntry> {
    return collectDescendantsOfType()
}

fun KtClass.findSealedClassInheritors(): List<PsiClass> {
    return ClassInheritorsSearch.search(toLightClass() as PsiClass).mapNotNull { it }
}