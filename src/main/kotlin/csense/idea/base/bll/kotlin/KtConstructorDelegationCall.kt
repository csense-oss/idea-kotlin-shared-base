package csense.idea.base.bll.kotlin

import org.jetbrains.kotlin.idea.references.*
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.utils.addToStdlib.*


fun KtConstructorDelegationCall.resolveConstructorCall(): KtConstructor<*>? =
    this.calleeExpression?.references?.firstIsInstance<KtReference>()?.resolve() as? KtConstructor<*>