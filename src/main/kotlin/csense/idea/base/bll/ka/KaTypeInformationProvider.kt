package csense.idea.base.bll.ka

import org.jetbrains.kotlin.analysis.api.components.*
import org.jetbrains.kotlin.analysis.api.types.*

fun KaTypeInformationProvider.isPrimitiveNumberOrNullableType(type: KaType): Boolean {
    return type.isIntType ||
            type.isLongType ||
            type.isFloatType ||
            type.isDoubleType ||
            type.isByteType ||
            type.isShortType ||
            type.isUIntType ||
            type.isULongType ||
            type.isUByteType ||
            type.isUShortType
}

fun KaTypeInformationProvider.fqClassNameAsString(type: KaType?): String? {
    return type?.symbol?.classId?.asString()
}