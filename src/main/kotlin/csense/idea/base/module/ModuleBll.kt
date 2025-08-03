@file:Suppress("unused")

package csense.idea.base.module

import com.intellij.openapi.module.*
import com.intellij.psi.*
import csense.idea.base.bll.platform.*
import org.jetbrains.kotlin.idea.util.*
import org.jetbrains.kotlin.psi.*


fun Module.findPackageDir(containingFile: KtFile): PsiDirectory? {
    val packageName = containingFile.packageFqName.asString()
    val psiDirectory = findKotlinRootDir() ?: return null
    return if (packageName == "") {
        psiDirectory
    } else {
        psiDirectory.findPackage(packageName)
    }
}


fun Module.findKotlinRootDir(): PsiDirectory? {
    val sourceRoot = sourceRoots.find {
        it.name == "kotlin"
    } ?: return null
    return sourceRoot.toPsiDirectory(project)
}