package csense.idea.base.bll.kotlin

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
        val superTypes = superTypeListEntries
        if (superTypes.isEmpty()) {
            return null
        }
        superTypes.forEach {
            when (val resolved = it.typeReference?.resolve()) {
                is KtLightClassForSourceDeclaration -> return resolved.kotlinOrigin
                is KtClassOrObject -> return resolved
            }
        }
        return null
    }

fun KtClassOrObject.isCompanion(): Boolean = hasModifier(KtTokens.COMPANION_KEYWORD)


fun KtClassOrObject.isUnit(): Boolean =
    isObjectLiteral() && fqName?.asString() == "kotlin.Unit"


fun KtClassOrObject.getAllClassProperties(): List<KtNamedDeclaration> {
    val localFields = collectDescendantsOfType<KtProperty> {
        !it.isLocal && (it.resolveType()?.isFunctionType ?: false)
    }
    val constructorFields: List<KtNamedDeclaration> = primaryConstructor?.let {
        it.collectDescendantsOfType { param: KtParameter ->
            param.hasValOrVar() && param.isFunctionalType()
        }
    } ?: listOf()
    return localFields + constructorFields
}