package csense.idea.base.bll.kotlin

import org.jetbrains.kotlin.psi.*


public inline fun KtPureElement.isStubbed(): Boolean {
    return containingKtFile.stub != null && containingKtFile.isCompiled
}