package csense.idea.base.module

import com.intellij.openapi.module.Module
import com.intellij.psi.PsiDirectory
import csense.idea.base.bll.toPsiDirectory
import org.jetbrains.kotlin.idea.util.sourceRoots
import org.jetbrains.kotlin.psi.KtFile


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
    return sourceRoot.toPsiDirectory(project) ?: return null
}