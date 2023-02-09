package csense.idea.base.bll.psiWrapper.function.operations

import csense.idea.base.bll.kotlin.*
import csense.idea.base.bll.psiWrapper.`class`.*
import csense.idea.base.bll.psiWrapper.`class`.operations.*
import csense.idea.base.bll.psiWrapper.function.*

fun KtPsiFunction.receiverTypeOrNull(): KtPsiClass? = when (this) {
    is KtPsiFunction.Kt -> function.receiverTypeReference?.resolve()?.asKtOrPsiClass()
    is KtPsiFunction.Psi -> null
}