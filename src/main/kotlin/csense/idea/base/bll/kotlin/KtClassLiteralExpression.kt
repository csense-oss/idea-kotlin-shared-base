package csense.idea.base.bll.kotlin

import com.intellij.psi.impl.source.*
import org.jetbrains.kotlin.psi.*
import org.jetbrains.uast.*
import org.jetbrains.uast.kotlin.*


fun KtClassLiteralExpression.findUClass(): UClass? {
    val uLiteral = toUElement() as? KotlinUClassLiteralExpression
    return (uLiteral?.type as? PsiClassReferenceType)?.reference?.resolve()?.toUElement() as? UClass
}