package csense.idea.base.bll.kotlin

import org.jetbrains.kotlin.idea.core.isOverridable
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.psiUtil.isAbstract

fun KtClass.isAbstractOrOpen(): Boolean {
    return isAbstract() || isOverridable()
}
