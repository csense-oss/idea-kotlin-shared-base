package csense.idea.base.bll.kotlin

import org.jetbrains.kotlin.descriptors.*
import org.jetbrains.kotlin.name.*
import org.jetbrains.kotlin.resolve.DescriptorUtils

fun DeclarationDescriptor.getFqName(): FqName {
    return DescriptorUtils.getFqNameSafe(this)
}