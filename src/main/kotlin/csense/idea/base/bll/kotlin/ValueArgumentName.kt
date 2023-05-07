package csense.idea.base.bll.kotlin

import org.jetbrains.kotlin.psi.*

fun ValueArgumentName.getNameAsString(): String = asName.asString()
