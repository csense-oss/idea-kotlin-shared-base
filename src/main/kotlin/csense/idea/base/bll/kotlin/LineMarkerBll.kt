package csense.idea.base.bll.kotlin

import com.intellij.psi.*
import com.intellij.psi.impl.source.tree.LeafPsiElement
import com.intellij.psi.util.*
import org.jetbrains.kotlin.lexer.*
import org.jetbrains.kotlin.psi.*


/**
 * Expected to be used in a linemarker provider function to extract the "expected" ktNameFunction
 */
fun PsiElement.getKtNamedFunctionFromLineMarkerIdentifierLeaf(): KtNamedFunction? {
    return getKtElementFromLineMarkerIdentifierLeaf()
}

fun PsiElement.getKtPropertyFromLineMarkerIdentifierLeaf(): KtProperty? {
    return getKtElementFromLineMarkerIdentifierLeaf()
}

inline fun <reified T: KtElement>PsiElement.getKtElementFromLineMarkerIdentifierLeaf(): T? {
    val isNotLeaf: Boolean = this !is LeafPsiElement
    if (isNotLeaf) {
        return null
    }
    if (elementType != KtTokens.IDENTIFIER) {
        return null
    }
    return parent as? T
}