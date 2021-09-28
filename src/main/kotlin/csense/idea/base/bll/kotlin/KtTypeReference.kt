package csense.idea.base.bll.kotlin

import com.intellij.psi.PsiElement
import csense.idea.base.mapIfInstance
import org.jetbrains.kotlin.psi.*

fun KtTypeReference.isFunctional(): Boolean = typeElement?.isFunctional() ?: false


/**
 * This will resolve PAST type alias's to resolve the real type.
 * @receiver KtTypeReference
 * @return PsiElement?
 */
fun KtTypeReference.resolve(): PsiElement? {
    return (typeElement as? KtUserType)
        ?.referenceExpression
        ?.references?.firstOrNull()
        ?.resolve()
        ?.mapIfInstance { it: KtTypeAlias ->
            it.getTypeReference()?.resolve()
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
fun KtTypeReference.resolveExcludingTypeAlias(): PsiElement? = (typeElement as? KtUserType)
    ?.referenceExpression
    ?.references?.firstOrNull()
    ?.resolve()
