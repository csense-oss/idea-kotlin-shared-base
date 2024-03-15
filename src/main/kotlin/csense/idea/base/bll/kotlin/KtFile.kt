package csense.idea.base.bll.kotlin

import csense.kotlin.extensions.primitives.*
import org.jetbrains.kotlin.idea.*
import org.jetbrains.kotlin.psi.*


fun KtFile.isKotlinFile(): Boolean {
    return this.virtualFilePath.endsWithAny(".kt", ".kts")
}

fun KtFile.isNotKotlinFile(): Boolean {
    return this.fileType != KotlinLanguage.INSTANCE.associatedFileType ||
            !isKotlinFile()
}