package csense.idea.base.bll.kotlin

import csense.idea.base.bll.psiWrapper.`class`.*
import csense.idea.base.bll.psiWrapper.`class`.operations.*
import org.jetbrains.kotlin.psi.*

fun List<ValueArgument>.resolveAsKClassTypes(): List<KtPsiClass> {
    return mapNotNull { annotation: ValueArgument ->
        //TODO. if for some cool reason the code contains "arrayOf" or alike (with star operator etc) then this will fail badly.
        annotation.getArgumentExpression()?.resolveFirstClassType2()
    }
}
fun ValueArgument.getArgumentNameAsString(): String? = getArgumentName()?.getNameAsString()