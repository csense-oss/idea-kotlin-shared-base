@file:Suppress("unused")

package csense.idea.base.bll.psiWrapper.`class`.operations.filter

import csense.idea.base.bll.psiWrapper.`class`.*
import csense.idea.base.bll.psiWrapper.`class`.operations.`is`.*


//TODO java and kotlin "common hierachies" not handled correctly in JVM case (where "Exception" != Exception & for throwable..)
fun List<KtPsiClass>.filterNonRelated(to: List<KtPsiClass>): List<KtPsiClass> {
    //TODO caching etc? compute a map of "classes" and then going over the other and testing?
    return this.filterNot {
        it.isSubTypeOfAny(to)
    }
}