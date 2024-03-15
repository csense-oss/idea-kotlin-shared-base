package csense.idea.base.bll.psiWrapper.`class`.operations.any

import csense.idea.base.bll.psiWrapper.`class`.*
import csense.idea.base.bll.psiWrapper.`class`.operations.*

inline fun KtPsiClass.anySuperClassType(
    predicate: (KtPsiClass) -> Boolean
): Boolean {
    forEachSuperClassType { it: KtPsiClass ->
        if (predicate(it)) {
            return@anySuperClassType true
        }
    }
    return false
}