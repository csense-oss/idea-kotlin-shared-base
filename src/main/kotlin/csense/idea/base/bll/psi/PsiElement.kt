@file:Suppress("unused")

package csense.idea.base.bll.psi

import com.intellij.pom.*
import com.intellij.psi.*
import csense.idea.base.bll.kotlin.*
import csense.kotlin.*
import csense.kotlin.extensions.*
import org.jetbrains.kotlin.asJava.*
import org.jetbrains.kotlin.name.*
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.psi.psiUtil.*

inline fun <reified T : PsiElement> PsiElement.findParentOfType(): T? {
    return findParentAndBeforeFromType<T>()?.first
}

inline fun <reified T : PsiElement> PsiElement.findParentAndBeforeFromType(): Pair<T, PsiElement>? {
    var currentElement: PsiElement? = this
    var previousType: PsiElement = this
    while (currentElement != null) {
        if (currentElement is T) {
            return Pair(currentElement, previousType)
        }
        previousType = currentElement
        currentElement = currentElement.parent
    }
    return null
}

inline fun <reified T : PsiElement> PsiElement.countDescendantOfType(
    crossinline predicate: (T) -> Boolean
): Int {
    var counter = 0
    forEachDescendantOfType<T> {
        if (predicate(it)) {
            counter += 1
        }
    }
    return counter
}

inline fun <reified T : PsiElement> PsiElement.haveDescendantOfType(
    noinline predicate: (T) -> Boolean
): Boolean {
    val found = findDescendantOfType<T> {
        it != this && predicate(it)
    }
    return found != this && found.isNotNull
}


//from https://github.com/JetBrains/kotlin/blob/master/idea/idea-analysis/src/org/jetbrains/kotlin/idea/refactoring/fqName/fqNameUtil.kt
/**
 * Returns FqName for given declaration (either Java or Kotlin)
 */
fun PsiElement.getKotlinFqName(): FqName? = when (val element = namedUnwrappedElement) {
    is PsiPackage -> FqName(element.qualifiedName)
    is PsiClass -> element.qualifiedName?.let { FqName(it) }
    is PsiMember -> element.getName()?.let { name ->
        val prefix = element.containingClass?.qualifiedName
        FqName(if (prefix != null) "$prefix.$name" else name)
    }

    is KtNamedDeclaration -> element.fqName
    else -> null
}

fun PsiElement.getKotlinFqNameString(): String? = getKotlinFqName()?.asString()


inline fun PsiElement.goUpUntil(parent: PsiElement, action: Function0<PsiElement>) {
    var current: PsiElement? = this.parent
    while (current != null && current != parent) {
        action(current)
        current = current.parent
    }
}

fun PsiElement.tryNavigate(requestFocus: Boolean) {
    if (this is Navigatable) {
        this.navigate(requestFocus)
    } else {
        val navElement = navigationElement as? Navigatable ?: return
        navElement.tryNavigate(requestFocus)
    }

}

val PsiElement.startOffset: Int
    get() = textRange.startOffset

val PsiElement.endOffset: Int
    get() = textRange.endOffset


fun PsiElement.addFirst(newElement: PsiElement): PsiElement? = addBefore(
    /* element = */ newElement,
    /* anchor = */ firstChild
)

fun PsiElement.resolveTypeAliasOrThis(): PsiElement? = when (this) {
    is KtTypeAlias -> getTypeReference()?.resolve()
    else -> this
}