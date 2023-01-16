package csense.idea.base.bll.kotlin

import org.jetbrains.kotlin.descriptors.CallableDescriptor
import org.jetbrains.kotlin.idea.caches.resolve.resolveToDescriptorIfAny
import org.jetbrains.kotlin.psi.KtDeclaration
import org.jetbrains.kotlin.types.*

//based on https://github.com/JetBrains/kotlin/blob/master/nj2k/nj2k-services/src/org/jetbrains/kotlin/nj2k/postProcessing/utils.kt#L36
fun KtDeclaration.resolveType(): KotlinType? =
    (resolveToDescriptorIfAny() as? CallableDescriptor)?.resolveType()
