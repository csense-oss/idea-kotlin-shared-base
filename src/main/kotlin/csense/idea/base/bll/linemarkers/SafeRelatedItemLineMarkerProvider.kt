@file:Suppress("unused")

package csense.idea.base.bll.linemarkers

import com.intellij.codeInsight.daemon.*
import com.intellij.openapi.progress.*
import com.intellij.psi.*
import com.intellij.psi.impl.source.tree.*

abstract class SafeRelatedItemLineMarkerProvider : RelatedItemLineMarkerProvider() {

    final override fun collectNavigationMarkers(
        element: PsiElement,
        currentLineMarkerInfos: MutableCollection<in RelatedItemLineMarkerInfo<*>>
    ) = catchAndIgnoreProcessCancellationException {

        if (element !is LeafPsiElement) {
            //documentation states that line-markers should only work on leaf psi elements....
            return@catchAndIgnoreProcessCancellationException
        }
        onCollectNavigationMarkers(
            element = element,
            result = currentLineMarkerInfos
        )
    }

    /**
     * The intention is to modify result (side effect) rather than computing a new result.
     */
    abstract fun onCollectNavigationMarkers(
        element: LeafPsiElement,
        result: MutableCollection<in RelatedItemLineMarkerInfo<*>>
    )
}

public inline fun catchAndIgnoreProcessCancellationException(
    action: () -> Unit
) {
    try {
        action()
    } catch (exception: ProcessCanceledException) {
        //ignored
    }
}