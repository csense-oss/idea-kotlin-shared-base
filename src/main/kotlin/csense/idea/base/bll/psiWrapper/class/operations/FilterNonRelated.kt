package csense.idea.base.bll.psiWrapper.`class`.operations

import csense.idea.base.bll.psiWrapper.`class`.*

fun List<KtPsiClass>.filterNonRelated(to: List<KtPsiClass>): List<KtPsiClass> {
    //TODO caching etc? compute a map of "classes" and then going over the other and testing?
    return this.filterNot {
        it.isSubTypeOfAny(to)
    }
}