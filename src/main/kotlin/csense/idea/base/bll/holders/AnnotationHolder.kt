@file:Suppress("unused")

package csense.idea.base.bll

import com.intellij.lang.annotation.*
import com.intellij.openapi.editor.markup.*
import com.intellij.openapi.util.*

fun AnnotationHolder.highlightTextRange(
    range: TextRange,
    withStyle: TextAttributes
) {
    newSilentAnnotation(/* severity = */ HighlightSeverity.INFORMATION)
        .range(range)
        .enforcedTextAttributes(withStyle)
        .create()
}