package csense.idea.base.bll.platform

import com.intellij.openapi.project.*
import com.intellij.openapi.vfs.*
import com.intellij.psi.*


fun VirtualFile.toPsiDirectory(
    project: Project
): PsiDirectory? = PsiManager.getInstance(project).findDirectory(this)