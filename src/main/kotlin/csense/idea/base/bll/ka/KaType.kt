package csense.idea.base.bll.ka

import org.jetbrains.kotlin.analysis.api.types.KaType
import org.jetbrains.kotlin.analysis.api.types.symbol

fun KaType.fqClassNameAsString(): String? {
    return symbol?.classId?.asString()?.replace(oldValue = "/", newValue = ".")
}