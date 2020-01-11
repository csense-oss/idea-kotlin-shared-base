@file:Suppress("NOTHING_TO_INLINE")

package csense.idea.base.bll.kotlin

import com.intellij.psi.PsiNamedElement
import csense.idea.base.UastKtPsi.resolvePsi
import csense.idea.base.bll.psi.countDescendantOfType
import org.jetbrains.kotlin.idea.core.isOverridable
import org.jetbrains.kotlin.idea.refactoring.isAbstract
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.psi.psiUtil.anyDescendantOfType
import org.jetbrains.kotlin.psi.psiUtil.containingClassOrObject
import org.jetbrains.kotlin.psi.psiUtil.findFunctionByName


fun KtNamedFunction.isOverriding(): Boolean {
    return modifierList?.isOverriding() ?: false
}

fun KtNamedFunction.isNotOverriding(): Boolean {
    return !isOverriding()
}

fun KtNamedFunction.isAbstractOrOpen(): Boolean {
    return isAbstract() || isOverridable()
}


//region have overloads
inline fun KtNamedFunction.haveOverloads(): Boolean {
    //use class or object if there, or if we are toplevel use the file to search.
    containingClassOrObject?.let {
        return haveOverloads(it)
    }
    return haveOverloads(containingKtFile)
}

inline fun KtNamedFunction.haveOverloads(containingFile: KtFile): Boolean {
    return containingFile.countDescendantOfType<KtNamedFunction> {
        it.isTopLevel && it.name == this.name
    } > 1
}

inline fun KtNamedFunction.haveOverloads(containerClassOrObject: KtClassOrObject): Boolean {
    return containerClassOrObject.countDescendantOfType<KtNamedFunction> {
        it.name == this.name
    } > 1
}
//endregion


fun KtNamedFunction.isBodyEmpty(): Boolean {
    return bodyExpression?.children?.isEmpty() ?: true
}

/**
 * Finds the overridden implementation, this means that interface or abstract functions are NOT returned.
 * @receiver KtNamedFunction
 * @return KtNamedFunction?
 */
fun KtNamedFunction.findOverridingImpl(): KtNamedFunction? {
    val name = this.name ?: return null
    val parentClass = this.containingClassOrObject ?: return null
    var currentParent: KtClassOrObject? = parentClass.superClass
    while (currentParent != null) {
        val currentParentFunction = currentParent.findFunctionByName(name) as? KtNamedFunction
        if (currentParentFunction != null && currentParentFunction.hasBody()) {
            return currentParentFunction
        }
        currentParent = parentClass.superClass
    }
    return null
}

/**
 * Tells if we have a super call that invokes this function name
 *
 * @receiver KtNamedFunction the function name to search for
 * @return Boolean true if there is a super call to this method name, false otherwise.
 */
fun KtNamedFunction.doesCallSuperFunction(): Boolean {
    val fncName = name ?: return false
    return anyDescendantOfType<KtSuperExpression>() {
        val superExp = it.parent as? KtDotQualifiedExpression
        val callExp = superExp?.selectorExpression as? KtCallExpression
        val resolved = callExp?.resolvePsi() as? PsiNamedElement
        val name = resolved?.name
        fncName == name
    }
}
