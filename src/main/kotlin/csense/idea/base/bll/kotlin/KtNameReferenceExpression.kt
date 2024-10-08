package csense.idea.base.bll.kotlin

import com.intellij.psi.PsiElement
import csense.kotlin.extensions.tryAndLog
import org.jetbrains.kotlin.idea.references.resolveMainReferenceToDescriptors
import org.jetbrains.kotlin.js.resolve.diagnostics.findPsi
import org.jetbrains.kotlin.nj2k.postProcessing.resolve
import org.jetbrains.kotlin.psi.*


fun KtNameReferenceExpression.isFunction(): Boolean {
    return tryAndLog{
        this.resolveMainReferenceToDescriptors().firstOrNull()?.findPsi() as? KtFunction != null
    } == true
}

fun KtNameReferenceExpression.isProperty(): Boolean = findPsi() as? KtProperty != null

fun KtNameReferenceExpression.findPsi(): PsiElement? {
    val referre = this.resolveMainReferenceToDescriptors().firstOrNull() ?: return null
    return referre.findPsi()
}


fun KtNameReferenceExpression.isAlias(): Boolean = resolveAliasType() != null

fun KtNameReferenceExpression.resolveAliasType(): KtTypeElement? {
    val asAlias = resolve() as? KtTypeAlias
    return asAlias?.getTypeReference()?.typeElement
}

fun KtNameReferenceExpression.isTypeAliasFunctional(): Boolean =
    resolveAliasType()?.isFunctional() ?: false

fun KtNameReferenceExpression.isMethodReference(): Boolean {
    return this.parent is KtDoubleColonExpression
}