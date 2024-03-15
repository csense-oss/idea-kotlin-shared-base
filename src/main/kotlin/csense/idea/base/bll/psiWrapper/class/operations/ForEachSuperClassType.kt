package csense.idea.base.bll.psiWrapper.`class`.operations

import csense.idea.base.bll.psiWrapper.`class`.*


inline fun KtPsiClass.forEachSuperClassType(
    onEachSuperType: (KtPsiClass) -> Unit
) {
    var current: KtPsiClass? = this.superClass()
    while (current != null) {
        onEachSuperType(current)
        current = current.superClass()
    }
}