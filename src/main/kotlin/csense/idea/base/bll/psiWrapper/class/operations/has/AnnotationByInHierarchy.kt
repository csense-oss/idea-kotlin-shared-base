package csense.idea.base.bll.psiWrapper.`class`.operations.has

import csense.idea.base.bll.psiWrapper.annotation.*
import csense.idea.base.bll.psiWrapper.`class`.*
import csense.idea.base.bll.psiWrapper.`class`.operations.*
import csense.idea.base.csense.*
import csense.kotlin.extensions.collections.generic.*

fun KtPsiClass.hasAnnotationByPredicateInClassHierarchy(predicate: (KtPsiAnnotation) -> Boolean): Boolean {
    return GenericCollectionExtensions.walkUpWithAny(
        startingPoint = this,
        getToNextLevelOrNull = KtPsiClass::superClass,
        predicate = { it: KtPsiClass ->
            it.hasAnnotationBy(predicate)
        }
    )
}

