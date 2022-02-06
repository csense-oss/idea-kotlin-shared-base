package csense.idea.base.bll.psi

import com.intellij.pom.*
import com.intellij.psi.*
import com.intellij.psi.search.GlobalSearchScope
import csense.idea.base.bll.getJavaLangThrowableUClass
import csense.kotlin.Function0
import csense.kotlin.extensions.isNotNull
import org.jetbrains.kotlin.asJava.namedUnwrappedElement
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.psi.KtNamedDeclaration
import org.jetbrains.kotlin.psi.psiUtil.findDescendantOfType
import org.jetbrains.kotlin.psi.psiUtil.forEachDescendantOfType
import org.jetbrains.uast.UClass
import org.jetbrains.uast.toUElementOfType

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

fun PsiElement.toUExceptionClass(cachedJavaLangThrowableUClass: UClass? = null): UClass? {
    return if (getKotlinFqNameString() == "kotlin.Throwable") {
        cachedJavaLangThrowableUClass ?: project.getJavaLangThrowableUClass()
    } else {
        toUElementOfType<UClass>()
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

