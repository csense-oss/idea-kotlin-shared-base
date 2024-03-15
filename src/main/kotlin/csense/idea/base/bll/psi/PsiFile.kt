package csense.idea.base.bll.psi

import com.intellij.openapi.module.*
import com.intellij.psi.*

fun PsiFile.findModule(): Module? {
    return ModuleUtil.findModuleForFile(this)
}