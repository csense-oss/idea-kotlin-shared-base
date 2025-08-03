@file:Suppress("unused")

package csense.idea.base.bll.kotlin

import com.intellij.psi.*
import com.intellij.psi.search.searches.*
import org.jetbrains.kotlin.asJava.*
import org.jetbrains.kotlin.idea.core.*
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.psi.psiUtil.*
import org.jetbrains.kotlin.psi.psiUtil.isAbstract

fun KtClass.isAbstractOrOpen(): Boolean {
    return isAbstract() || isOverridable()
}

fun KtClass.getEnumValues(): List<KtEnumEntry> {
    return collectDescendantsOfType()
}

fun KtClass.findSealedClassInheritors(): List<PsiClass> {
    return ClassInheritorsSearch.search(toLightClass() as PsiClass).toList()
}