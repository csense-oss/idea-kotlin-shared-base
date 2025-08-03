@file:Suppress("unused")

package csense.idea.base.module

import com.intellij.psi.*
import com.intellij.util.*
import csense.kotlin.extensions.*
import org.jetbrains.kotlin.psi.*


fun PsiDirectory.findPackage(packageName: String): PsiDirectory? {
    if (packageName.isEmpty()) {
        return null
    }
    val folders = packageName.split(".")
    var resultingDirectory = this
    folders.forEach {
        resultingDirectory = resultingDirectory.findSubdirectory(it) ?: return@findPackage null
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
    return@tryAndLog dir
}

fun PsiDirectory.findPackageDir(file: KtFile): PsiDirectory? {
    val packageName = file.packageFqName.asString()
    return if (packageName == "") {
        this
    } else {
        this.findPackage(packageName)
    }
}