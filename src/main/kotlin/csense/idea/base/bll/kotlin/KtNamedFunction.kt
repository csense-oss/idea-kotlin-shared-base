@file:Suppress("NOTHING_TO_INLINE", "unused")

package csense.idea.base.bll.kotlin

import com.intellij.psi.*
import csense.idea.base.bll.psi.*
import csense.kotlin.extensions.primitives.*
import org.jetbrains.kotlin.idea.core.*
import org.jetbrains.kotlin.idea.refactoring.*
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.psi.psiUtil.*


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
    //use class or object if there, or if we are top level use the file to search.
    containingClassOrObject?.let {
        return@haveOverloads haveOverloads(it)
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
        currentParent = currentParent.superClass
    }
    return null
}




/**
 * Tries to convert an body expression function to a block body function
 * @receiver KtNamedFunction what function to create a copy of with a block body instead of a body expression
 * @param factory KtPsiFactory the factory to use to create the real KtNamedFunction
 * @param manipulateBodyCode Function1<String, String> if you want to inject code before / after
 * and or manipulate the existing code
 * @return KtNamedFunction
 */
fun KtNamedFunction.convertToBlockFunction(
    factory: KtPsiFactory,
    manipulateBodyCode: Function1<String, String> = { it }
): KtNamedFunction {
    val bodyExp = bodyExpression ?: return this
    val text = text
    val equalIndex = text.indexOfOrNull("=", 0, false) ?: return this
    val subStrTo = text.substring(0, equalIndex)
    val end = subStrTo.lastIndexOf(")")
    if (end == -1) {
        return this
    }

    val computedResultText = if (subStrTo.indexOf(':', end) != -1) {
        ""
    } else {
        val computedReturnType = bodyExp.computeTypeAsString() ?: "Unit"
        ":$computedReturnType"
    }
    val bodyCode = manipulateBodyCode(bodyExp.text)
    val superFncText = subStrTo + "$computedResultText{\n${bodyCode}\n}"
    return factory.createFunction(superFncText)
}


@Deprecated("use resolveClassType2()")
fun KtNamedFunction.getDeclaredReturnType(): PsiElement? {
    return typeReference?.resolve()
}

fun KtNamedFunction.isReceiver(): Boolean =
    receiverTypeReference != null

