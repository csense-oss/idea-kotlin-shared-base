package csense.idea.base.bll.psiWrapper.function

import com.intellij.psi.*
import org.jetbrains.kotlin.psi.*

sealed interface KtPsiFunction {

    val fqName: String?

    class Psi(
        val function: PsiMethod
    ) : KtPsiFunction {
        override val fqName: String = function.name
    }

    class Kt(
        val function: KtFunction
    ) : KtPsiFunction {
        override val fqName: String? = function.fqName?.asString()
    }
}

