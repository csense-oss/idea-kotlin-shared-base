package csense.idea.base.bll.kotlin

import com.intellij.psi.impl.source.PsiClassReferenceType
import org.jetbrains.kotlin.psi.KtClassLiteralExpression
import org.jetbrains.uast.UClass
import org.jetbrains.uast.kotlin.KotlinUClassLiteralExpression
import org.jetbrains.uast.toUElement


fun KtClassLiteralExpression.findUClass(): UClass? {
    val uLiteral = toUElement() as? KotlinUClassLiteralExpression
    return (uLiteral?.type as? PsiClassReferenceType)?.reference?.resolve()?.toUElement() as? UClass
}
