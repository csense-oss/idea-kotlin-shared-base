@file:Suppress("unused")

package csense.idea.base.bll.kotlin

import com.intellij.psi.*
import csense.kotlin.extensions.*
import org.jetbrains.kotlin.psi.*

fun KtTypeReference.isFunctional(): Boolean = typeElement?.isFunctional() ?: false


/**
 * This will resolve PAST type alias's to resolve the real type.
 * @receiver KtTypeReference
 * @return PsiElement?
 */
@Deprecated("use resolveFirstClassType2 instead")
fun KtTypeReference.resolve(): PsiElement? {
    return (typeElement as? KtUserType)
        ?.referenceExpression
        ?.references?.firstNotNullOfOrNull { it: PsiReference? ->
            it?.resolve()?.mapIfInstanceOrThis { alias: KtTypeAlias ->
                alias.getTypeReference()?.resolve()
            }
        }
}

fun KtTypeReference.isNullableType(): Boolean {
    return typeElement is KtNullableType
}

/**
 * This will stop at type aliases as well as classes ect.
 * @receiver KtTypeReference
 * @return PsiElement?
 */
@Deprecated("use resolveFirstClassType2 instead")
fun KtTypeReference.resolveExcludingTypeAlias(): PsiElement? = (typeElement as? KtUserType)
    ?.referenceExpression
    ?.references?.firstOrNull()
    ?.resolve()
