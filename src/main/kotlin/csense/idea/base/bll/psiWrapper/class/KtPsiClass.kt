package csense.idea.base.bll.psiWrapper.`class`

import com.intellij.openapi.project.*
import com.intellij.psi.*
import com.intellij.psi.search.*
import csense.idea.base.bll.psiWrapper.`class`.operations.*
import org.jetbrains.kotlin.idea.stubindex.*
import org.jetbrains.kotlin.psi.*

//consider caching "questions" (eg subtypes etc)
sealed interface KtPsiClass {
    val fqName: String?
    val shortName: String?
    val project: Project

    class Psi(val psiClass: PsiClass) : KtPsiClass {
        override val fqName: String? = psiClass.qualifiedName
        override val shortName: String? = psiClass.name
        override val project: Project = psiClass.project
    }

    class Kt(val ktClassOrObject: KtClassOrObject) : KtPsiClass {
        override val fqName: String? = ktClassOrObject.fqName?.asString()
        override val shortName: String? = ktClassOrObject.name
        override val project: Project = ktClassOrObject.project
    }

    companion object
}
