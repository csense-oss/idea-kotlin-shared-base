@file:Suppress("NOTHING_TO_INLINE")

package csense.idea.base.bll.kotlin

import csense.idea.base.bll.psi.countDescendantOfType
import org.jetbrains.kotlin.idea.core.isOverridable
import org.jetbrains.kotlin.idea.refactoring.isAbstract
import org.jetbrains.kotlin.psi.KtClassOrObject
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.psi.psiUtil.containingClassOrObject


fun KtNamedFunction.isOverriding(): Boolean {
    return modifierList?.isOverriding() ?: false
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