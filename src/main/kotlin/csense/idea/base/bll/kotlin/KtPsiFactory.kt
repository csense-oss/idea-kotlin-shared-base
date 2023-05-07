@file:Suppress("unused")

package csense.idea.base.bll.kotlin

import org.intellij.lang.annotations.*
import org.jetbrains.kotlin.psi.*

fun KtPsiFactory.createCatchClause(
    catchExpression: String,
    body: String = "TODO(\"Add error handling here\")"
): KtCatchClause? {
    @Language("kotlin")
    val wrapperCode = """
        try {
            
        }catch($catchExpression){
            $body
        }
    """.trimIndent()
    val tryExpression: KtTryExpression? = createBlock(wrapperCode).firstStatement as? KtTryExpression
    return tryExpression?.catchClauses?.singleOrNull()
}


fun KtPsiFactory.createThrowsAnnotation(): KtAnnotationEntry {
    return createAnnotationEntry("@Throws()")
}