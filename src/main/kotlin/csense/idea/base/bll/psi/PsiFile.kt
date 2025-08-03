package csense.idea.base.bll.psi

import com.intellij.openapi.module.*
import com.intellij.psi.*
import csense.idea.base.bll.kotlin.*
import org.jetbrains.kotlin.psi.*

fun PsiFile.findModule(): Module? {
    return ModuleUtil.findModuleForFile(this)
}

fun PsiFile.isKtFileStubbed(): Boolean {
    return this is KtFile && this.isStubbed()
}