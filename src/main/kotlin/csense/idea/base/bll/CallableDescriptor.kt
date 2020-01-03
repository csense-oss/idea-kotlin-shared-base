package csense.idea.base.bll

import com.intellij.psi.PsiClass
import com.intellij.psi.util.PsiTreeUtil
import org.jetbrains.kotlin.descriptors.CallableDescriptor
import org.jetbrains.kotlin.js.resolve.diagnostics.findPsi
import org.jetbrains.uast.UClass
import org.jetbrains.uast.toUElement

/**
 *
 * @return List<String> the order of the arguments as well as the name
 */
fun CallableDescriptor.findOriginalMethodArgumentNames(): List<String> {
    return valueParameters.map { param ->
        param.name.asString()
    }
}

fun CallableDescriptor.findUClass(): UClass? {
    val psi = findPsi() ?: return null
    return if (psi is PsiClass) {
        psi.toUElement(UClass::class.java)
    } else {
        PsiTreeUtil.getParentOfType(psi, PsiClass::class.java)?.toUElement(UClass::class.java)
    }
}