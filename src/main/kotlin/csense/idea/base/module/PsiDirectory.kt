package csense.idea.base.module

import com.intellij.psi.PsiDirectory
import com.intellij.util.IncorrectOperationException
import csense.kotlin.extensions.tryAndLog


fun PsiDirectory.findPackage(packageName: String): PsiDirectory? {
    if (packageName.isEmpty()) {
        return null
    }
    val folders = packageName.split(".")
    var resultingDirectory = this
    folders.forEach {
        resultingDirectory = resultingDirectory.findSubdirectory(it) ?: return null
    }
    return resultingDirectory
}


fun PsiDirectory.createPackageFolders(packageName: String): PsiDirectory? = tryAndLog {
    val names = packageName.split('.')
    var dir = this
    names.forEach {
        try {
            dir = dir.findSubdirectory(it) ?: dir.createSubdirectory(it)
        } catch (e: IncorrectOperationException) {
            //TODO handle.
            return@tryAndLog null
        }
    }
    return dir
}