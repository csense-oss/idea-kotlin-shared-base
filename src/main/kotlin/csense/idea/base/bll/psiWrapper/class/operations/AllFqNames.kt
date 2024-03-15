package csense.idea.base.bll.psiWrapper.`class`.operations

import csense.idea.base.bll.psiWrapper.`class`.*
import csense.idea.base.csense.*
import csense.kotlin.extensions.*

fun List<KtPsiClass>.allFqNames(): Set<String> {

    val aliasFqNames: Set<String?> = mapToSet { it.typeAlias?.fqName?.asString() }

    val fqNames: Set<String?> = mapToSet { it.fqName }
    return aliasFqNames.filterNotNullSet() + fqNames.filterNotNullSet()

}