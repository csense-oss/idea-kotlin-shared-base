package csense.idea.base.bll.psiWrapper.function

import com.intellij.psi.*
import org.jetbrains.kotlin.psi.*

sealed interface KtPsiFunction {

    class Psi(
        val function: PsiMethod
    ) : KtPsiFunction

    class Kt(
        val function: KtFunction
    ) : KtPsiFunction
}

