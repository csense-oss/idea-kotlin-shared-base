@file:Suppress("unused")

package csense.idea.base.bll.openApi

import com.intellij.openapi.util.TextRange

fun TextRange(offset: Int, forString: String): TextRange = TextRange(
    offset = offset,
    length = forString.length
)

fun TextRange(offset: Int, length: Int): TextRange = TextRange(
    /* startOffset = */ offset,
    /* endOffset = */ offset + length
)

