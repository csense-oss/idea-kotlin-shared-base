@file:Suppress("unused")

package csense.idea.base.bll.psiWrapper.`class`.operations

import csense.idea.base.bll.psiWrapper.`class`.*
import org.intellij.lang.annotations.*

fun List<KtPsiClass>.coloredFqNameString(
    cssColor: String,
    tagType: String = "b",
    separator: String = ", "
): String {
    @Language("html")
    val typePrefix = "<$tagType style='color: $cssColor'>"
    return joinToString(
        separator = "</$tagType>${separator}${typePrefix}",
        prefix = typePrefix,
        postfix = "</$tagType>",
        transform = { ktPsiClass: KtPsiClass ->
            ktPsiClass.getFqNameTypeAliased().orEmpty()
        }
    )
}