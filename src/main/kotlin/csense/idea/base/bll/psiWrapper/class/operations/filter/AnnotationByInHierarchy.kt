package csense.idea.base.bll.psiWrapper.`class`.operations.filter

import csense.idea.base.bll.psiWrapper.annotation.*
import csense.idea.base.bll.psiWrapper.`class`.*
import csense.idea.base.bll.psiWrapper.`class`.operations.*
import csense.idea.base.csense.*
import csense.kotlin.extensions.collections.generic.*

fun KtPsiClass.filterAnnotationsByPredicateInClassHierarchy(
    filter: (KtPsiAnnotation) -> Boolean
): List<KtPsiAnnotation> {
    val result: MutableList<KtPsiAnnotation> = mutableListOf()
    GenericCollectionExtensions.walkUpWith(
        startingPoint = this,
        getToNextLevelOrNull = KtPsiClass::superClass,
        onEachLevel = { it: KtPsiClass ->
            result += it.filterAnnotationsBy(filter)
        }
    )
    return result
}

