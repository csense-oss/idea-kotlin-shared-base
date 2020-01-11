package csense.idea.base.bll.kotlin

import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.idea.references.resolveMainReferenceToDescriptors
import org.jetbrains.kotlin.js.resolve.diagnostics.findPsi
import org.jetbrains.kotlin.lexer.KtTokens
import org.jetbrains.kotlin.psi.KtClassOrObject
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.psi.KtProperty
import org.jetbrains.kotlin.psi.psiUtil.collectDescendantsOfType

fun KtClassOrObject.getProperties() = body?.properties.orEmpty()

fun KtClassOrObject.findNonDelegatingProperties(): List<KtProperty> {
    return getProperties().filterNot { prop -> prop.hasDelegate() }
}


fun KtClassOrObject.isSealed(): Boolean = hasModifier(KtTokens.SEALED_KEYWORD)

fun KtClassOrObject.isAbstract(): Boolean = hasModifier(KtTokens.ABSTRACT_KEYWORD)

fun KtClassOrObject.isAnonymous(): Boolean = name == null

fun KtClassOrObject.getAllFunctions(): List<KtNamedFunction> = collectDescendantsOfType()

val KtClassOrObject.superClass: KtClassOrObject?
    get() {
        val superTypes = superTypeListEntries
        if (superTypes.isEmpty()) {
            return null
        }
        superTypes.forEach {
            val realClass = it.typeAsUserType?.referenceExpression?.resolveMainReferenceToDescriptors()
                ?.firstOrNull()?.containingDeclaration?.findPsi() as? KtClassOrObject
            if (realClass != null) {
                return realClass
            }
        }
        return null
    }

fun KtClassOrObject.isCompanion(): Boolean = hasModifier(KtTokens.COMPANION_KEYWORD)