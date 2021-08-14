package csense.idea.base.bll.kotlin

import com.intellij.psi.*
import com.intellij.psi.impl.source.PsiClassReferenceType
import com.intellij.psi.search.GlobalSearchScope
import org.jetbrains.kotlin.nj2k.postProcessing.resolve
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.psi.psiUtil.containingClass

fun PsiElement.resolveRealType(): PsiElement? {
    return when (this) {
        is KtElement -> resolveRealType()
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

fun KtProperty.resolveRealType(): PsiElement? {
    val type = typeReference
    if (type != null) {
        return type.resolve()?.resolveRealType()
    }
    val init = initializer
    if (init != null) {
        return init.resolveRealType()
    }
    val getter = getter
    if (getter != null) {
        return getter.resolveRealType()
    }
    return null
}

tailrec fun KtElement.resolveRealType(): PsiElement? {
    return when (this) {
        is KtCallExpression -> {
            val ref = resolveMainReferenceWithTypeAlias()
            ref?.resolveRealType()
        }
        is KtDotQualifiedExpression -> rightMostSelectorExpression()?.resolveRealType()
        is KtProperty -> resolveRealType()
        is KtSecondaryConstructor, is KtPrimaryConstructor -> {
            this.containingClass()
        }
        is KtReferenceExpression -> {
            resolve()?.resolveRealType()
        }
        is KtNamedFunction -> {
            this.getDeclaredReturnType()
        }
        else -> null
    }
}