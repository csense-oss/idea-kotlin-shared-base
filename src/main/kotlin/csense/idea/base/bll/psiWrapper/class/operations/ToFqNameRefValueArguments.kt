@file:Suppress("unused")

package csense.idea.base.bll.psiWrapper.`class`.operations

import com.intellij.openapi.project.*
import com.intellij.psi.PsiFile
import csense.idea.base.bll.psiWrapper.`class`.*
import org.jetbrains.kotlin.psi.*

fun List<KtPsiClass>.toFqNameRefValueArguments(
    project: Project,
    forFile: PsiFile
): List<KtValueArgument> {
    val factory = KtPsiFactory(
        project = project,
        markGenerated = false
    )
    return map { it: KtPsiClass ->
        factory.createArgument(it.nameRef(forFile))
    }
}
