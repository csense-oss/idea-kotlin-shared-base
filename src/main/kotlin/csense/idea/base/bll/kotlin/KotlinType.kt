package csense.idea.base.bll.kotlin

import csense.kotlin.extensions.*
import org.jetbrains.kotlin.name.*
import org.jetbrains.kotlin.types.*

tailrec fun KotlinType.fqName(): FqName? {
    if (this.isNot<AbbreviatedType>()) {
        return constructor.declarationDescriptor?.getFqName()
    }
    return this.getAbbreviation()?.fqName()
}

fun KotlinType.fqNameAsString(): String? {
    return fqName()?.asString()
}