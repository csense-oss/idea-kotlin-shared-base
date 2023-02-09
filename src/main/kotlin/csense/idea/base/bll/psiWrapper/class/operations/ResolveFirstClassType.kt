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

fun PsiElement.resolveFirstClassType2(): KtPsiClass? {
    return when (this) {
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
            return JavaPsiFacade.getInstance(project)
                .findClass(
                    this.type.canonicalText,
                    this.type.resolveScope ?: GlobalSearchScope.allScope(project)
                )?.asKtOrPsiClass()
        }
        else -> null
    }
}

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

tailrec fun KtElement.resolveFirstClassType2(): KtPsiClass? {
    return when (this) {
        is KtClass -> asKtOrPsiClass()
        is KtCallExpression -> {
            val ref: PsiElement? = resolveMainReferenceWithTypeAlias()
            ref?.resolveFirstClassType2()
        }

        is KtDotQualifiedExpression -> rightMostSelectorExpression()?.resolveFirstClassType2()
        is KtProperty -> resolveFirstClassType2()
        is KtSecondaryConstructor, is KtPrimaryConstructor -> {
            //TODO make containingClass our own.
            containingClass()?.asKtOrPsiClass()
        }

        is KtNameReferenceExpression -> references.firstOrNull()?.resolveFirstClassType2()
        is KtReferenceExpression -> resolve()?.resolveFirstClassType2()
        is KtNamedFunction -> getDeclaredReturnType()?.asKtOrPsiClass()
        is KtCallableReferenceExpression -> callableReference.resolveFirstClassType2()
        //TODO should be "first" non null instead of assuming the first is the right one?
        else -> null
    }
}

fun PsiReference.resolveFirstClassType2(): KtPsiClass? = resolve()?.resolveFirstClassType2()
