package csense.idea.base.bll.psiWrapper.imports


sealed interface KtPsiImports {
    val import: String

    data class Kt(
        override val import: String
    ) : KtPsiImports

    data class Psi(
        override val import: String
    ) : KtPsiImports
}

