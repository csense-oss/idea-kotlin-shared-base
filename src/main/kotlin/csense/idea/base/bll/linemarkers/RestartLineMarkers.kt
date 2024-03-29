@file:Suppress("unused")

package csense.idea.base.bll.linemarkers

import com.intellij.codeInsight.daemon.*
import com.intellij.openapi.project.*
import com.intellij.psi.*

/**
 * An easier way to remember how to "restart" the line marker(s)
 */
fun restartLineMarkers(forProject: Project, forFile: PsiFile? = null) {
    if (forFile != null) {
        DaemonCodeAnalyzer.getInstance(forProject).restart(forFile)
        return
    }
    DaemonCodeAnalyzer.getInstance(forProject).restart()
}

fun restartLineMarkersForAllProjects() {
    ProjectManager.getInstance().openProjects.forEach {
        DaemonCodeAnalyzer.getInstance(it).restart()
    }
}