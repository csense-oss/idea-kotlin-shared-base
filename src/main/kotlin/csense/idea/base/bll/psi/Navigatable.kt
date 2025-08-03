@file:Suppress("unused", "INVISIBLE_MEMBER", "INVISIBLE_REFERENCE")

package csense.idea.base.bll.psi

import com.intellij.pom.*
import kotlin.internal.*

@LowPriorityInOverloadResolution
fun Navigatable.tryNavigate(requestFocus: Boolean) {
    if (canNavigate()) {
        navigate(requestFocus)
    }
}