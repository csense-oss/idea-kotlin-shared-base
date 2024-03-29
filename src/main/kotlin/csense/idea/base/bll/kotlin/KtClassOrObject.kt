@file:Suppress("unused")

package csense.idea.base.bll.kotlin

import com.intellij.psi.*
import org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration
import org.jetbrains.kotlin.builtins.isFunctionType
import org.jetbrains.kotlin.lexer.KtTokens
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.psi.psiUtil.collectDescendantsOfType
import org.jetbrains.kotlin.psi.psiUtil.isObjectLiteral

fun KtClassOrObject.getProperties() = body?.properties.orEmpty()

fun KtClassOrObject.findNonDelegatingProperties(): List<KtProperty> {
    return getProperties().filterNot { prop -> prop.hasDelegate() }
}


fun KtClassOrObject.isSealed(): Boolean = hasModifier(KtTokens.SEALED_KEYWORD)

fun KtClassOrObject.isAbstract(): Boolean = hasModifier(KtTokens.ABSTRACT_KEYWORD)

fun KtClassOrObject.isAnonymous(): Boolean = name == null

fun KtClassOrObject.getAllFunctions(): List<KtNamedFunction> = collectDescendantsOfType()

/**
 *
 */
val KtClassOrObject.superClass: KtClassOrObject?
    get() {
        val superTypes: List<KtSuperTypeListEntry> = superTypeListEntries
        if (superTypes.isEmpty()) {
            return null
        }
        superTypes.forEach { it: KtSuperTypeListEntry ->
            when (val resolved: PsiElement? = it.typeReference?.resolve()) {
                is KtLightClassForSourceDeclaration -> return resolved.kotlinOrigin
                is KtClassOrObject -> return resolved
                is KtSecondaryConstructor -> return resolved.getContainingClassOrObject()
                is KtPrimaryConstructor -> return resolved.getContainingClassOrObject()
            }
        }
        return null
    }

fun KtClassOrObject.isCompanion(): Boolean = hasModifier(KtTokens.COMPANION_KEYWORD)


fun KtClassOrObject.isUnit(): Boolean =
    isObjectLiteral() && fqName?.asString() == "kotlin.Unit"


fun KtClassOrObject.getAllClassProperties(): List<KtNamedDeclaration> {
    val localFields: List<KtProperty> = collectDescendantsOfType<KtProperty> {
        !it.isLocal && (it.resolveType()?.isFunctionType ?: false)
    }
    val constructorFields: List<KtNamedDeclaration> = primaryConstructor?.let {
        it.collectDescendantsOfType { param: KtParameter ->
            param.hasValOrVar() && param.isFunctionalType()
        }
    } ?: listOf()
    return localFields + constructorFields
}
