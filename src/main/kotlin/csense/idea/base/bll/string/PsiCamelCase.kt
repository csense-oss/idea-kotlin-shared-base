@file:Suppress("unused")

package csense.idea.base.bll.string

import com.intellij.openapi.util.*
import com.intellij.psi.*

val PsiElement.camelCase: PsiCamelCase
    get() = PsiCamelCase(this)


@JvmInline
value class PsiCamelCase(val psiElement: PsiElement)


fun PsiCamelCase.forEachCamelCaseWordWithTextRange(
    onCamelCase: (range: TextRange, camelCaseWord: String) -> Unit
) {

    val startOffsetOfText: Int = psiElement.textRange.startOffset

    psiElement.text.camelCase.forEachCamelCaseWord { stringStartIndex: Int, string: String ->
        val currentStartIndex: Int = startOffsetOfText + stringStartIndex
        val range: TextRange = TextRange(
            /* startOffset = */  currentStartIndex,
            /* endOffset = */ currentStartIndex + string.length
        )
        onCamelCase(range, string)
    }
}
