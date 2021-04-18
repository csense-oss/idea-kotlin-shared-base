package csense.idea.base.bll.kotlin

import csense.idea.base.bll.psi.findParentOfType
import org.jetbrains.kotlin.psi.KtClassOrObject
import org.jetbrains.kotlin.psi.KtElement
import org.jetbrains.kotlin.psi.KtObjectDeclaration

fun KtElement.isInObject(): Boolean =
    this.findParentOfType<KtClassOrObject>() is KtObjectDeclaration