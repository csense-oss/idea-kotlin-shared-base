package csense.idea.base.bll.kotlin

import com.intellij.psi.*
import com.intellij.psi.impl.source.*
import com.intellij.psi.search.*
import csense.idea.base.bll.psiWrapper.`class`.operations.*
import org.jetbrains.kotlin.nj2k.postProcessing.*
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.psi.psiUtil.*

fun PsiElement.resolveFirstClassType(): PsiElement? {
    return when (this) {
        is KtElement -> resolveFirstClassType()
        is PsiClass -> this
        is PsiMethod -> {
            if (this.isConstructor) {
                this.containingClass
            } else {
                (returnType as? PsiClassReferenceType)?.resolve()
            }

        }
        //TODO improve?
        is PsiField -> {
            val project = project
            return JavaPsiFacade.getInstance(project)
                .findClass(this.type.canonicalText, this.type.resolveScope ?: GlobalSearchScope.allScope(project))
        }

        else -> null
    }
}

fun KtProperty.resolveFirstClassType(): PsiElement? {
    val type = typeReference
    if (type != null) {
        return type.resolve()?.resolveFirstClassType()
    }
    val init = initializer
    if (init != null) {
        return init.resolveFirstClassType()
    }
    val getter = getter
    if (getter != null) {
        return getter.resolveFirstClassType()
    }
    return null
}

tailrec fun KtElement.resolveFirstClassType(): PsiElement? {
    return when (this) {
        is KtClass -> return this
        is KtCallExpression -> {
            val ref = resolveMainReferenceWithTypeAlias()
            ref?.resolveFirstClassType()
        }

        is KtDotQualifiedExpression -> rightMostSelectorExpression()?.resolveFirstClassType()
        is KtProperty -> resolveFirstClassType()
        is KtSecondaryConstructor, is KtPrimaryConstructor -> {
            this.containingClass()
        }

        is KtNameReferenceExpression -> this.references.firstNotNullOfOrNull { it.resolveFirstClassType() } //for class literals the first reference is a synthetic...
        is KtReferenceExpression -> {
            resolve()?.resolveFirstClassType()
        }

        is KtNamedFunction -> {
            this.getDeclaredReturnType()
        }

        is KtCallableReferenceExpression -> callableReference.resolveFirstClassType()
        is KtClassLiteralExpression -> receiverExpression?.resolveFirstClassType()
        //TODO should be "first" non null instead of assuming the first is the right one?
        else -> null
    }
}

fun PsiReference.resolveFirstClassType(): PsiElement? = resolve()?.resolveFirstClassType()
