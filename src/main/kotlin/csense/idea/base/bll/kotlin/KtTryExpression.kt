@file:Suppress("unused")

package csense.idea.base.bll.kotlin

import com.intellij.psi.*
import org.jetbrains.kotlin.psi.*


fun KtTryExpression.addCatchClausesLast(clauses: List<KtCatchClause>) {
    clauses.forEach(::addCatchClauseLast)
}

fun KtTryExpression.addCatchClauseLast(clause: KtCatchClause): PsiElement? = addAfter(
    /* element = */ clause,
    /* anchor = */ catchClauses.lastOrNull() ?: lastChild //TODO hmm...?
)