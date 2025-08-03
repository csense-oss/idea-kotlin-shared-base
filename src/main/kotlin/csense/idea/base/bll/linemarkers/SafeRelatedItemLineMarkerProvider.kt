@file:Suppress("unused")

package csense.idea.base.bll.linemarkers

import com.intellij.codeInsight.daemon.*
import com.intellij.psi.*
import com.intellij.psi.impl.source.tree.*
import csense.idea.base.bll.*
import csense.kotlin.extensions.*

abstract class SafeRelatedItemLineMarkerProvider : RelatedItemLineMarkerProvider() {

    final override fun collectNavigationMarkers(
        element: PsiElement,
        currentLineMarkerInfos: MutableCollection<in RelatedItemLineMarkerInfo<*>>
    ): Unit = tryAndIgnoreProcessCanceledException {

        if (element !is LeafPsiElement) {
            //documentation states that line-markers should only work on leaf psi elements....
            return@tryAndIgnoreProcessCanceledException
        }
        onCollectNavigationMarkers(
            element = element,
            result = currentLineMarkerInfos
        )
    }.toUnit()

    /**
     * The intention is to modify result (side effect) rather than computing a new result.
     */
    abstract fun onCollectNavigationMarkers(
        element: LeafPsiElement,
        result: MutableCollection<in RelatedItemLineMarkerInfo<*>>
    )
}