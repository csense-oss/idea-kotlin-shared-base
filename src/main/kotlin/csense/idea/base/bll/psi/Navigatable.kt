package csense.idea.base.bll.psi

import com.intellij.pom.*

fun Navigatable.tryNavigate(requestFocus: Boolean) {
    if (canNavigate()) {
        navigate(requestFocus)
    }
}