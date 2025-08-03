package csense.idea.base.bll.kotlin

import csense.kotlin.extensions.*
import org.jetbrains.kotlin.analysis.api.analyze
import org.jetbrains.kotlin.name.*
import org.jetbrains.kotlin.types.*

tailrec fun KotlinType.fqName(): FqName? {
    if (this.isNot<AbbreviatedType>()) {
        TODO("return constructor.")
    }
    return this.getAbbreviation()?.fqName()
}

fun KotlinType.fqNameAsString(): String? {
    return fqName()?.asString()
}