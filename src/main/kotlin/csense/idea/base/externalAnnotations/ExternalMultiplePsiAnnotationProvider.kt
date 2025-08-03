package csense.idea.base.externalAnnotations

import com.intellij.codeInsight.externalAnnotation.*
import com.intellij.openapi.project.*
import com.intellij.psi.*


abstract class ExternalMultiplePsiAnnotationProvider(private val name: String) : AnnotationProvider {
    override fun getName(project: Project?): String = name

    abstract fun isUsableForType(owner: PsiModifierListOwner?): Boolean

    override fun isAvailable(owner: PsiModifierListOwner?): Boolean =
        owner?.hasAnnotation(name) != true && isUsableForType(owner)

}

abstract class MultiplePsiAnnotationProvider(private val name: String) : AnnotationProvider {
    override fun getName(project: Project?): String = name

    abstract fun isUsableForType(owner: PsiModifierListOwner?): Boolean

    override fun isAvailable(owner: PsiModifierListOwner?): Boolean =
        owner?.hasAnnotation(name) != true && isUsableForType(owner)

}