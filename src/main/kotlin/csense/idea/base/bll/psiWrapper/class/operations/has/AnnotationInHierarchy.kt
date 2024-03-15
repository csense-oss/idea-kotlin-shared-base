package csense.idea.base.bll.psiWrapper.`class`.operations.has

import csense.idea.base.bll.psiWrapper.`class`.*
import csense.idea.base.bll.psiWrapper.`class`.operations.*
import csense.idea.base.csense.*
import csense.kotlin.extensions.collections.generic.*

fun KtPsiClass.hasAnnotationByFqNameInHierarchy(fqName: String): Boolean {
    return GenericCollectionExtensions.walkUpAny(
        startingPoint = this,
        getToNextLevelOrNull = KtPsiClass::superClass,
        predicate = { it: KtPsiClass ->
            it.hasAnnotationBy(fqName)
        }
    )
}
