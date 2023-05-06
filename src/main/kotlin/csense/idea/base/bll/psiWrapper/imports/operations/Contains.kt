package csense.idea.base.bll.psiWrapper.imports.operations

import csense.idea.base.bll.psiWrapper.imports.*

private val builtInKotlinImports: Set<String> = setOf(
    "kotlin" // by default all things in the "kotlin" namespace are imported
)


fun KtPsiImports.contains(fqName: String, className: String): Boolean = when (this) {
    is KtPsiImports.Kt -> contains(fqName = fqName, className = className)
    is KtPsiImports.Psi -> contains(fqName = fqName, className = className)
}

fun KtPsiImports.Psi.contains(fqName: String, className: String): Boolean {
    TODO()
}

fun KtPsiImports.Kt.contains(fqName: String, className: String): Boolean {
    val namespace: String = fqName.removeSuffix(className).removeSuffix(".")
    if (namespace in builtInKotlinImports) {
        return true
    }
    return import == "$namespace.*" || import == fqName
}