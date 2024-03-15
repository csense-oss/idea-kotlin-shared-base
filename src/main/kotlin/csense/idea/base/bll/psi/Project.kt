package csense.idea.base.bll.psi

import com.intellij.openapi.project.*
import com.intellij.openapi.roots.*

val Project.fileIndexService: ProjectFileIndex
    get() = ProjectFileIndex.SERVICE.getInstance(this)
