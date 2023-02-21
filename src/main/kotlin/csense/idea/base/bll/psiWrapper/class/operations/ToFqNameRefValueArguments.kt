@file:Suppress("unused")

package csense.idea.base.bll.psiWrapper.`class`.operations

import com.intellij.openapi.project.*
import csense.idea.base.bll.psiWrapper.`class`.*
import org.jetbrains.kotlin.psi.*

fun List<KtPsiClass>.toFqNameRefValueArguments(project: Project): List<KtValueArgument> {
    val factory = KtPsiFactory(
        project = project,
        markGenerated = false
    )
    return map { it: KtPsiClass ->
        factory.createArgument(it.fqNameRef())
    }
}
