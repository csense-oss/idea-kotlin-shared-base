package csense.idea.base.bll.psiWrapper.`class`.operations

import csense.idea.base.bll.psi.*
import csense.idea.base.bll.psiWrapper.`class`.*


fun KtPsiClass.className(): String = when (this) {
    is KtPsiClass.Kt -> ktClassOrObject.name ?: ktClassOrObject.getKotlinFqNameString() ?: ""
    is KtPsiClass.Psi -> psiClass.name ?: psiClass.getKotlinFqNameString() ?: ""
}