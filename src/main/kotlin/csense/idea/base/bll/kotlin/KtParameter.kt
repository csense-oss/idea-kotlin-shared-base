package csense.idea.base.bll.kotlin

import csense.idea.base.bll.uast.isChildOfSafe
import org.jetbrains.kotlin.descriptors.ValueDescriptor
import org.jetbrains.kotlin.idea.caches.resolve.resolveToDescriptorIfAny
import org.jetbrains.kotlin.js.resolve.diagnostics.findPsi
import org.jetbrains.kotlin.psi.KtParameter
import org.jetbrains.uast.UClass
import org.jetbrains.uast.toUElement

fun KtParameter.resolveTypeClass(): UClass? {
    return (this.resolveToDescriptorIfAny() as? ValueDescriptor)
        ?.type
        ?.constructor
        ?.declarationDescriptor
        ?.findPsi()
        ?.toUElement(UClass::class.java)
}

fun KtParameter.isSubtypeOf(otherType: UClass): Boolean {
    val caughtClass = resolveTypeClass()
        ?: return false
    return otherType.isChildOfSafe(caughtClass)
}
