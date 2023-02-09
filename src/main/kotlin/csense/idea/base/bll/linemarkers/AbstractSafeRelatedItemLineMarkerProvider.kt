@file:Suppress("unused")

package csense.idea.base.bll.linemarkers

import com.intellij.codeInsight.daemon.*
import com.intellij.psi.*
import com.intellij.psi.impl.source.tree.*
import csense.idea.base.bll.annotator.*

abstract class AbstractSafeRelatedItemLineMarkerProvider<T : PsiElement>(
    private val classType: Class<T>
) : SafeRelatedItemLineMarkerProvider() {
    final override fun onCollectNavigationMarkers(
        element: LeafPsiElement,
        result: MutableCollection<in RelatedItemLineMarkerInfo<*>>
    ) {
        val parent: PsiElement = element.parent ?: return
        val casted: T = classType.tryCast(parent) ?: return
        onCollectNavigationMarkersFor(
            typedElement = casted,
            leafPsiElement = element,
            result = result
        )
    }

    abstract fun onCollectNavigationMarkersFor(
        typedElement: T,
        leafPsiElement: LeafPsiElement,
        result: MutableCollection<in RelatedItemLineMarkerInfo<*>>
    )

}