@file:Suppress("unused", "INVISIBLE_MEMBER", "INVISIBLE_REFERENCE")

package csense.idea.base.bll.psi

import kotlin.internal.*
import com.intellij.pom.*

@LowPriorityInOverloadResolution
fun Navigatable.tryNavigate(requestFocus: Boolean) {
    if (canNavigate()) {
        navigate(requestFocus)
    }
}