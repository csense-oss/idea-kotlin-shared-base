package csense.idea.base.uastKtPsi

import com.intellij.codeInsight.ExternalAnnotationsManager
import csense.idea.base.bll.uast.computeSuperAnnotations
import org.jetbrains.kotlin.psi.KtClassOrObject
import org.jetbrains.uast.UAnnotation
import org.jetbrains.uast.UClass
import org.jetbrains.uast.toUElementOfType

fun KtClassOrObject.computeSuperAnnotations(extManager: ExternalAnnotationsManager): List<UAnnotation> {
    return this.toUElementOfType<UClass>()?.computeSuperAnnotations(extManager) ?: emptyList()
}