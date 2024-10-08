@file:Suppress("unused")

package csense.idea.base.bll.kotlin

import csense.idea.base.bll.psiWrapper.function.*
import csense.idea.base.bll.psiWrapper.function.operations.*
import org.jetbrains.kotlin.lexer.*
import org.jetbrains.kotlin.psi.*


fun KtSimpleNameExpression.resolveAsKtPsiFunction(): KtPsiFunction? {
    return references.firstNotNullOfOrNull {
        it.resolve()?.toKtPsiFunction()
    }
}

fun KtSimpleNameExpression.resolveAsKtProperty(): KtProperty? {
    return references.firstNotNullOfOrNull {
        it.resolve() as? KtProperty
    }
}



fun KtSimpleNameExpression.containsNotToken(): Boolean = getReferencedNameElementType() in setOf(
    KtTokens.NOT_IN,
    KtTokens.NOT_IS,
    KtTokens.EXCL,
    KtTokens.EXCLEQ,
    KtTokens.EXCLEQEQEQ
)