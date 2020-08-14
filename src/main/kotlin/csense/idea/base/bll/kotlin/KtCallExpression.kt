@file:Suppress("unused")

package csense.idea.base.bll.kotlin

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiMember
import com.intellij.psi.PsiMethod
import org.jetbrains.kotlin.idea.references.mainReference
import org.jetbrains.kotlin.psi.*
import org.jetbrains.uast.UClass
import org.jetbrains.uast.toUElement


/**
 * Find all argument names in order they are declared.
 * @receiver KtCallExpression
 * @return List<String?>
 */
fun KtCallExpression.findInvocationArgumentNamesNew(): List<String?> {
    return valueArguments.map { it: KtValueArgument? ->
        it?.getArgumentExpression()?.resolvePotentialArgumentName()
    }
}

/**
 * Used to try and find a potential name for a given argument. (
 * @receiver KtExpression
 * @return String?
 */
fun KtExpression.resolvePotentialArgumentName(): String? = when (this) {
    is KtNameReferenceExpression -> getReferencedName()
    is KtDotQualifiedExpression -> {
        val lhs = receiverExpression as? KtNameReferenceExpression
        val rhs = selectorExpression as? KtNameReferenceExpression
        rhs?.resolvePotentialArgumentName() ?: lhs?.resolvePotentialArgumentName()
    }//todo callexpression,akk class with a name then something in that.
    //akk
    /*
    data class Bottom(val top: Double)
    data class Top(val bottom: Bottom)
    fun use(top:Double) {}
    fun test() {
        use(Top(Bottom(42.0)).bottom.top)
    }
    */
    else -> null
}


/**
 * Resolves the original method.
 * @receiver KtCallExpression
 * @return PsiElement?
 */
fun KtCallExpression.resolveMainReference(): PsiElement? {
    return calleeExpression?.mainReference?.resolve()
}

fun KtCallExpression.resolveMainReferenceWithTypeAlias(): PsiElement? {
    val resolved = calleeExpression?.mainReference?.resolve()
    return if (resolved is KtTypeAlias) {
        resolved.getTypeReference()?.resolve()
    } else {
        resolved
    }
}

fun KtCallExpression.resolveMainReferenceWithTypeAliasForClass(): UClass? {
    val resolved = resolveMainReferenceWithTypeAlias()
    val clz = if (resolved is PsiMember) {
        resolved.containingClass
    } else {
        resolved
    }
    return clz?.toUElement(UClass::class.java)
}

/**
 * Resolves the original method.
 * @receiver KtCallExpression
 * @return PsiElement?
 */
fun KtCallExpression.resolveMainReferenceAsKtFunction(): KtFunction? {
    return resolveMainReference() as? KtFunction
}

/**
 * Resolves the original method.
 * @receiver KtCallExpression
 * @return PsiElement?
 */
fun KtCallExpression.resolveMainReferenceAsPsiMethod(): PsiMethod? {
    return resolveMainReference() as? PsiMethod
}
