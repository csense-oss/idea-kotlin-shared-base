package csense.idea.base.UastKtPsi

import com.intellij.psi.PsiClass
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiMember
import com.intellij.psi.PsiPackage
import org.jetbrains.kotlin.asJava.namedUnwrappedElement
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.psi.KtConstantExpression
import org.jetbrains.kotlin.psi.KtExpression
import org.jetbrains.kotlin.psi.KtNamedDeclaration
import org.jetbrains.kotlin.psi.KtPrefixExpression
import org.jetbrains.uast.UExpression

//from https://github.com/JetBrains/kotlin/blob/master/idea/idea-analysis/src/org/jetbrains/kotlin/idea/refactoring/fqName/fqNameUtil.kt
/**
 * Returns FqName for given declaration (either Java or Kotlin)
 */
fun PsiElement.getKotlinFqName(): FqName? = when (val element = namedUnwrappedElement) {
    is PsiPackage -> FqName(element.qualifiedName)
    is PsiClass -> element.qualifiedName?.let(::FqName)
    is PsiMember -> element.getName()?.let { name ->
        val prefix = element.containingClass?.qualifiedName
        FqName(if (prefix != null) "$prefix.$name" else name)
    }
    is KtNamedDeclaration -> element.fqName
    else -> null
}

fun PsiElement.getKotlinFqNameString():String? = getKotlinFqName()?.asString()

fun UExpression.asLong(): Long? = asT(kotlin.String::toLongOrNull)
fun UExpression.asInt(): Int? = asT(kotlin.String::toIntOrNull)
fun UExpression.asDouble(): Double? = asT(kotlin.String::toDoubleOrNull)
fun UExpression.asFloat(): Float? = asT(kotlin.String::toFloatOrNull)
fun UExpression.asByte(): Byte? = asT(kotlin.String::toByteOrNull)
fun UExpression.asShort(): Short? = asT(kotlin.String::toShortOrNull)

fun KtExpression.asLong(): Long? = asT(String::toLongOrNull)
fun KtExpression.asInt(): Int? = asT(String::toIntOrNull)
fun KtExpression.asDouble(): Double? = asT(String::toDoubleOrNull)
fun KtExpression.asFloat(): Float? = asT(String::toFloatOrNull)
fun KtExpression.asByte(): Byte? = asT(String::toByteOrNull)
fun KtExpression.asShort(): Short? = asT(String::toShortOrNull)

//this looks like a hack.... hmm
inline fun <reified T> UExpression.asT(converter: (String) -> T?): T? {
    val evaluated = this.evaluate()
    if (evaluated is T) {
        return evaluated
    }
    return evaluated?.toString()?.let(converter)
}

inline fun <reified T> KtExpression.asT(converter: (String) -> T?): T? {
    return when (this) {
        is KtPrefixExpression -> converter(this.text)
        is KtConstantExpression -> converter(this.text)
        else -> null
    }
}


