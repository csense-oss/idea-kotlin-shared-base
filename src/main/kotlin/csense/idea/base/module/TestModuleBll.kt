@file:Suppress("unused")

package csense.idea.base.module

import com.intellij.openapi.module.*
import com.intellij.openapi.project.*
import com.intellij.openapi.roots.*
import com.intellij.openapi.vfs.*
import com.intellij.psi.*
import csense.idea.base.bll.platform.*
import csense.kotlin.extensions.primitives.*
import org.jetbrains.kotlin.config.*
import org.jetbrains.kotlin.idea.base.facet.*
import org.jetbrains.kotlin.idea.util.*
import org.jetbrains.kotlin.idea.util.projectStructure.*

fun PsiElement.isInTestModule(): Boolean {
    //works for android & idea 203 + 213
    return TestSourcesFilter.isTestSources(
        /* file = */ containingFile.virtualFile,
        /* project = */ project
    )
}


fun Module.isTestModule(): Boolean {
    //TODO use testSourcesFilter or study isInTestSourceContent's docs..
    if (kotlinSourceRootType == TestSourceKotlinRootType) {
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