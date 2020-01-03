package csense.idea.base.module

import com.intellij.openapi.module.Module
import com.intellij.openapi.module.ModuleUtil
import com.intellij.openapi.project.rootManager
import com.intellij.psi.PsiElement
import csense.kotlin.extensions.primitives.doesNotEndsWith
import org.jetbrains.kotlin.idea.util.projectStructure.allModules



fun PsiElement.isInTestModule(): Boolean {
    val module = ModuleUtil.findModuleForPsiElement(this) ?: return false
    return module.isTestModule()
}


fun Module.isTestModule(): Boolean {
    return name.endsWith("_test") || name.endsWith(".test")
}


fun PsiElement.findTestModule(): Module? {
    val module = ModuleUtil.findModuleForPsiElement(this) ?: return null
    //step 2 is to find the test file in the test root
    if (module.isTestModule()) {
        return null
    }


    val searchingFor = module.name
    return this.project.allModules().find { mod: Module ->
        val modName = mod.name
        //if the name starts with teh same and
        //  &&mod.testSourceInfo() != null
        if (modName.doesNotEndsWith("test", true) || modName.length < 4) {
            return@find false
        }
        if (!mod.rootManager.isDependsOn(module)) {
            return@find false
        }

        val withoutTestIndex = modName.length - 4
        val withoutTest = modName.substring(0, withoutTestIndex)
        searchingFor.startsWith(withoutTest)
    }
}

