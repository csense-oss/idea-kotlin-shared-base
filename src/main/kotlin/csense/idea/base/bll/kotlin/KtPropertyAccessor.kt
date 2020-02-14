package csense.idea.base.bll.kotlin

import org.jetbrains.kotlin.psi.KtClassOrObject
import org.jetbrains.kotlin.psi.KtProperty
import org.jetbrains.kotlin.psi.psiUtil.containingClassOrObject
import org.jetbrains.kotlin.psi.psiUtil.findPropertyByName

/**
 * Finds the overridden implementation, this means that interface or abstract functions are NOT returned.
 * @receiver KtNamedFunction
 * @return KtNamedFunction?
 */
fun KtProperty.findOverridingImpl(): KtProperty? {
    val name = this.name ?: return null
    val parentClass = this.containingClassOrObject ?: return null
    var currentParent: KtClassOrObject? = parentClass.superClass
    while (currentParent != null) {
        val currentParentFunction = currentParent.findPropertyByName(name) as? KtProperty
        if (currentParentFunction != null) {
            return currentParentFunction
        }
        currentParent = parentClass.superClass
    }
    return null
}