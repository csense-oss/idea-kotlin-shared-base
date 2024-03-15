@file:Suppress("unused")

package csense.idea.base.module

import com.intellij.openapi.module.Module
import com.intellij.openapi.module.ModuleUtil
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ModuleRootManager
import com.intellij.openapi.roots.ProjectFileIndex
import com.intellij.openapi.roots.TestSourcesFilter
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiElement
import csense.idea.base.bll.platform.toPsiDirectory
import csense.kotlin.extensions.primitives.doesNotEndsWith
import org.jetbrains.kotlin.idea.caches.project.SourceType
import org.jetbrains.kotlin.idea.caches.project.sourceType
import org.jetbrains.kotlin.idea.util.projectStructure.allModules
import org.jetbrains.kotlin.idea.util.sourceRoots

fun PsiElement.isInTestModule(): Boolean {
    //works for android & idea 203 + 213
    return TestSourcesFilter.isTestSources(
        /* file = */ containingFile.virtualFile,
        /* project = */ project
    )
}


fun Module.isTestModule(): Boolean {
    //TODO use testSourcesFilter or study isInTestSourceContent's docs..
    if (sourceType == SourceType.TEST) {
        return true
    }
    val rootMgr: ModuleRootManager = ModuleRootManager.getInstance(this)
    return rootMgr.getSourceRoots(false).isEmpty() &&
            rootMgr.getSourceRoots(true).isNotEmpty()
}


fun Module.findMostPropableTestModule(): Module? {
    val searchingFor: String = this.name
    return this.project.allModules().find { mod: Module ->
        val modName: String = mod.name
        //if the name starts with the same and ends with test
        if (modName.doesNotEndsWith("test", true) || modName.length < 4) {
            return@find false
        }

        if (!ModuleRootManager.getInstance(mod).isDependsOn(this)) {
            return@find false
        }

        val withoutTestIndex: Int = modName.length - 4
        val withoutTest: String = modName.substring(startIndex = 0, endIndex = withoutTestIndex)
        searchingFor.startsWith(withoutTest)
    }
}


fun PsiElement.findMostPropableTestSourceRoot(): PsiDirectory? {
    val module: Module = ModuleUtil.findModuleForPsiElement(this) ?: return null
    return module.findMostPropableTestSourceRootDir()
}

fun Module.findMostPropableTestSourceRootDir(): PsiDirectory? {
    return (findMostPropableTestSourceRoot()
        ?: findMostPropableTestModule()?.findMostPropableTestSourceRoot())?.toPsiDirectory(project)
}

fun Module.findMostPropableTestSourceRoot(): VirtualFile? {
    val testSourceRoots: List<VirtualFile> = sourceRoots.filterTestSourceRoots(project)
    return testSourceRoots.findMostPreferedTestSourceRootForKotlin()
}

/**
 * Will first find the kotlin folder, then the java then if non matches, the first if any
 * @receiver List<VirtualFile>
 * @return VirtualFile?
 */
fun List<VirtualFile>.findMostPreferedTestSourceRootForKotlin(): VirtualFile? {
    return firstOrNull { it: VirtualFile ->
        it.name.equals(other = "kotlin", ignoreCase = true)
    } ?: firstOrNull { it: VirtualFile ->
        it.name.equals(other = "java", ignoreCase = true)
    } ?: firstOrNull()
}

fun Array<VirtualFile>.filterTestSourceRoots(project: Project): List<VirtualFile> {
    val inst: ProjectFileIndex = ProjectFileIndex.SERVICE.getInstance(project)
    return filter { it: VirtualFile ->
        inst.isInTestSourceContent(it)
    }
}