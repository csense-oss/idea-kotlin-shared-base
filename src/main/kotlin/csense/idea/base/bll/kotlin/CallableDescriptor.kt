package csense.idea.base.bll.kotlin

import org.jetbrains.kotlin.descriptors.CallableDescriptor
import org.jetbrains.kotlin.types.*

fun CallableDescriptor.resolveType(): KotlinType? = returnType