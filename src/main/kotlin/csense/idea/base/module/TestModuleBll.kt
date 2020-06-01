package csense.idea.base.module

import com.intellij.openapi.module.Module
import com.intellij.openapi.module.ModuleUtil
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.rootManager
import com.intellij.openapi.roots.ModuleRootManager
import com.intellij.openapi.roots.ProjectFileIndex
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiElement
import csense.idea.base.bll.toPsiDirectory
import csense.kotlin.extensions.primitives.doesNotEndsWith
import org.jetbrains.kotlin.idea.util.projectStructure.allModules
import org.jetbrains.kotlin.idea.util.sourceRoots


fun PsiElement.isInTestSourceRoot(): Boolean {
    val file = this.containingFile ?: return false
    return ProjectFileIndex.SERVICE.getInstance(project).isInTestSourceContent(file.virtualFile)
}

fun PsiElement.isInTestModule(): Boolean {
    val module = ModuleUtil.findModuleForPsiElement(this) ?: return false
    return module.isTestModule()
}


fun Module.isTestModule(): Boolean {
    return name.endsWith("_test") || name.endsWith(".test")
}


fun Module.findMostPropableTestModule(): Module? {
    val searchingFor = this.name
    return this.project.allModules().find { mod: Module ->
        val modName = mod.name
        //if the name starts with the same and ends with test
        if (modName.doesNotEndsWith("test", true) || modName.length < 4) {
            return@find false
        }

        if (!ModuleRootManager.getInstance(mod).isDependsOn(this)) {
            return@find false
        }

        val withoutTestIndex = modName.length - 4
        val withoutTest = modName.substring(0, withoutTestIndex)
        searchingFor.startsWith(withoutTest)
    }
}


fun PsiElement.findMostPropableTestSourceRoot(): PsiDirectory? {
    val module = ModuleUtil.findModuleForPsiElement(this) ?: return null
    //step 2 is to find the test file in the test root
    if (module.isTestModule()) {
        return null
    }
    return module.findMostPropableTestSourceRootDir()
}

fun Module.findMostPropableTestSourceRootDir(): PsiDirectory? {
    return (findMostPropableTestSourceRoot()
        ?: findMostPropableTestModule()?.findMostPropableTestSourceRoot())?.toPsiDirectory(project)
}

fun Module.findMostPropableTestSourceRoot(): VirtualFile? {
    //strategy for sourceRoot searching
    val testSourceRoots = sourceRoots.filterTestSourceRoots(project)
    return testSourceRoots.findMostPreferedTestSourceRootForKotlin()
}

/**
 * Will first find the kotlin folder, then the java then if non matches, the first if any
 * @receiver List<VirtualFile>
 * @return VirtualFile?
 */
fun List<VirtualFile>.findMostPreferedTestSourceRootForKotlin(): VirtualFile? {
    return firstOrNull {
        it.name.equals("kotlin", true)
    } ?: firstOrNull {
        it.name.equals("java", true)
    } ?: firstOrNull()
}

fun Array<VirtualFile>.filterTestSourceRoots(project: Project): List<VirtualFile> {
    val inst = ProjectFileIndex.SERVICE.getInstance(project)
    return filter {
        inst.isInTestSourceContent(it)
    }
}

