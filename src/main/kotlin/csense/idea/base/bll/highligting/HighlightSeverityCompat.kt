@file:Suppress("unused")

package csense.idea.base.bll.highligting

import com.intellij.lang.annotation.*


object HighlightSeverityCompat

@Suppress("UnusedReceiverParameter")
fun HighlightSeverityCompat.fromName(
    name: String,
    default: HighlightSeverity
): HighlightSeverity = HighlightSeverity.DEFAULT_SEVERITIES.firstOrNull {
    it.name == name
} ?: default
