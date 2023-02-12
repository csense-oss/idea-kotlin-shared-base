package csense.idea.base.bll.psiWrapper.`class`.operations

import com.intellij.psi.*
import com.intellij.psi.impl.source.*
import com.intellij.psi.search.*
import csense.idea.base.bll.kotlin.*
import csense.idea.base.bll.psiWrapper.`class`.*
import org.jetbrains.kotlin.nj2k.postProcessing.*
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.psi.psiUtil.*

//TODO better name / replace other function?!

//shouldResolveTypeAlias only works for kotlin. unless java one day gets a typealias operator / construct...
fun PsiElement.resolveFirstClassType2(
    shouldResolveTypeAlias: Boolean = false
): KtPsiClass? {
    return when (this) {
        is KtElement -> resolveFirstClassType2(shouldResolveTypeAlias)
        is PsiClass -> asKtOrPsiClass()
        is PsiMethod -> {
            if (this.isConstructor) {
                this.containingClass?.asKtOrPsiClass()
            } else {
                (returnType as? PsiClassReferenceType)?.resolve()?.asKtOrPsiClass()
            }

        }
        //TODO improve?
        is PsiField -> {
            val project = project
            return JavaPsiFacade.getInstance(project)
                .findClass(
                    this.type.canonicalText,
                    this.type.resolveScope ?: GlobalSearchScope.allScope(project)
                )?.asKtOrPsiClass()
        }

        else -> null
    }
}

fun KtProperty.resolveFirstClassType2(
    shouldResolveTypeAlias: Boolean = false
): KtPsiClass? {
    val type: KtTypeReference? = typeReference
    if (type != null) {
        return type.resolve()?.resolveFirstClassType2(shouldResolveTypeAlias)
    }
    val init: KtExpression? = initializer
    if (init != null) {
        return init.resolveFirstClassType2(shouldResolveTypeAlias)
    }
    val getter: KtPropertyAccessor? = getter
    if (getter != null) {
        return getter.resolveFirstClassType2(shouldResolveTypeAlias)
    }
    return null
}

tailrec fun KtElement.resolveFirstClassType2(
    shouldResolveTypeAlias: Boolean = false
): KtPsiClass? {
    return when (this) {
        is KtClass -> resolveFirstClassType2()
        is KtCallExpression -> resolveFirstClassType2(shouldResolveTypeAlias)
        is KtDotQualifiedExpression -> rightMostSelectorExpression()?.resolveFirstClassType2(shouldResolveTypeAlias)
        is KtProperty -> resolveFirstClassType2(shouldResolveTypeAlias)
        is KtConstructor<*> -> {
            //TODO make containingClass our own.
            containingClass()?.asKtOrPsiClass()
        }

        is KtNameReferenceExpression -> resolveFirstClassType2(shouldResolveTypeAlias)
        is KtReferenceExpression -> resolveFirstClassType2(shouldResolveTypeAlias)
        is KtNamedFunction -> resolveFirstClassType2()
        is KtCallableReferenceExpression -> resolveFirstClassType2(shouldResolveTypeAlias)
        is KtClassLiteralExpression -> resolveFirstClassType2(shouldResolveTypeAlias)
        is KtTypeAlias -> getTypeReference()?.resolve()?.resolveFirstClassType2(shouldResolveTypeAlias)

        //TODO should be "first" non null instead of assuming the first is the right one?
        else -> null
    }
}


fun KtClass.resolveFirstClassType2(): KtPsiClass.Kt = asKtOrPsiClass()

fun KtCallExpression.resolveFirstClassType2(
    shouldResolveTypeAlias: Boolean = false
): KtPsiClass? {
    //TODO?! shouldResolveTypeAlias ??
    val ref: PsiElement? = resolveMainReferenceWithTypeAlias()
    return ref?.resolveFirstClassType2(shouldResolveTypeAlias)
}





fun KtNameReferenceExpression.resolveFirstClassType2(
    shouldResolveTypeAlias: Boolean = false
): KtPsiClass? = references.firstNotNullOfOrNull { it.resolveFirstClassType2(shouldResolveTypeAlias) }

fun KtCallableReferenceExpression.resolveFirstClassType2(
    shouldResolveTypeAlias: Boolean = false
): KtPsiClass? = callableReference.resolveFirstClassType2(shouldResolveTypeAlias)

fun KtClassLiteralExpression.resolveFirstClassType2(
    shouldResolveTypeAlias: Boolean = false
): KtPsiClass? = receiverExpression?.resolveFirstClassType2(shouldResolveTypeAlias)


fun KtNamedFunction.resolveFirstClassType2(
): KtPsiClass? = getDeclaredReturnType()?.asKtOrPsiClass()

fun KtReferenceExpression.resolveFirstClassType2(
    shouldResolveTypeAlias: Boolean = false
): KtPsiClass? = resolve()?.resolveFirstClassType2(shouldResolveTypeAlias)

fun PsiReference.resolveFirstClassType2(
    shouldResolveTypeAlias: Boolean = false
): KtPsiClass? = resolve()?.resolveFirstClassType2(shouldResolveTypeAlias)
