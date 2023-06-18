package csense.idea.base.bll.psiWrapper.`class`.operations

import com.intellij.psi.*
import com.intellij.psi.impl.source.*
import com.intellij.psi.search.*
import csense.idea.base.bll.kotlin.*
import csense.idea.base.bll.psiWrapper.`class`.*
import org.jetbrains.kotlin.nj2k.postProcessing.*
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.psi.psiUtil.*

fun PsiElement.resolveFirstClassType2(): KtPsiClass? = when (this) {
    is KtElement -> resolveFirstClassType2()
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
        JavaPsiFacade.getInstance(project)
            .findClass(
                this.type.canonicalText,
                this.type.resolveScope ?: GlobalSearchScope.allScope(project)
            )?.asKtOrPsiClass()
    }

    else -> null
}


fun KtElement.resolveFirstClassType2(): KtPsiClass? = when (this) {
    is KtUserType -> resolveFirstClassType2()
    is KtClass -> resolveFirstClassType2()
    is KtCallExpression -> resolveFirstClassType2()
    is KtDotQualifiedExpression -> resolveFirstClassType2()
    is KtProperty -> resolveFirstClassType2()
    is KtConstructor<*> -> resolveFirstClassType2()
    is KtNameReferenceExpression -> resolveFirstClassType2()
    is KtReferenceExpression -> resolveFirstClassType2()
    is KtNamedFunction -> resolveFirstClassType2()
    is KtCallableReferenceExpression -> resolveFirstClassType2()
    is KtClassLiteralExpression -> resolveFirstClassType2()
    is KtTypeAlias -> resolveFirstClassType2()
    is KtParameter -> resolveFirstClassType2()
    is KtAnnotationEntry -> resolveFirstClassType2()
    is KtSuperTypeListEntry -> resolveFirstClassType2()
    is KtTypeReference -> resolveFirstClassType2()
    else -> null
}


fun KtSuperTypeListEntry.resolveFirstClassType2(): KtPsiClass? {
    return typeReference?.resolveFirstClassType2()
}

fun KtTypeReference.resolveFirstClassType2(): KtPsiClass? {
    return typeElement?.resolveFirstClassType2()
}

fun KtUserType.resolveFirstClassType2(): KtPsiClass? {
    return referenceExpression?.resolveFirstClassType2()
}

fun KtClass.resolveFirstClassType2(): KtPsiClass.Kt = asKtOrPsiClass()

fun KtCallExpression.resolveFirstClassType2(): KtPsiClass? {
    val mainRef: PsiElement? = resolveMainReference()
    val resolved: PsiElement? = mainRef?.resolveTypeAliasOrThis()
    val result: KtPsiClass? = resolved?.resolveFirstClassType2()
    if (mainRef is KtTypeAlias && result != null) {
        return result.withTypeAlias(mainRef)
    }
    return result
}

fun KtDotQualifiedExpression.resolveFirstClassType2(): KtPsiClass? =
    rightMostSelectorExpression()?.resolveFirstClassType2()


fun KtProperty.resolveFirstClassType2(): KtPsiClass? {
    val type: KtTypeReference? = typeReference
    if (type != null) {
        return type.resolve()?.resolveFirstClassType2()
    }
    val init: KtExpression? = initializer
    if (init != null) {
        return init.resolveFirstClassType2()
    }
    val getter: KtPropertyAccessor? = getter
    if (getter != null) {
        return getter.resolveFirstClassType2()
    }
    return null
}

fun KtConstructor<*>.resolveFirstClassType2(): KtPsiClass? =
    //TODO make containingClass our own.
    containingClass()?.asKtOrPsiClass()

fun KtNameReferenceExpression.resolveFirstClassType2(): KtPsiClass? =
    references.firstNotNullOfOrNull { it: PsiReference? ->
        it?.resolveFirstClassType2()
    }

fun KtReferenceExpression.resolveFirstClassType2(): KtPsiClass? =
    resolve()?.resolveFirstClassType2()

fun KtNamedFunction.resolveFirstClassType2(): KtPsiClass? =
    getDeclaredReturnType()?.asKtOrPsiClass()

fun KtCallableReferenceExpression.resolveFirstClassType2(): KtPsiClass? =
    callableReference.resolveFirstClassType2()


fun KtClassLiteralExpression.resolveFirstClassType2(): KtPsiClass? =
    receiverExpression?.resolveFirstClassType2()

fun KtTypeAlias.resolveFirstClassType2(): KtPsiClass? {
    val ref: PsiElement? = getTypeReference()?.resolve()
    val result: KtPsiClass = ref?.resolveFirstClassType2() ?: return null
    return result.withTypeAlias(typeAlias = this)
}

fun KtParameter.resolveFirstClassType2(): KtPsiClass? =
    typeReference?.resolve()?.resolveFirstClassType2()

fun PsiReference.resolveFirstClassType2(): KtPsiClass? =
    resolve()?.resolveFirstClassType2()

fun KtAnnotationEntry.resolveFirstClassType2(): KtPsiClass? =
    typeReference?.resolve()?.resolveFirstClassType2()

