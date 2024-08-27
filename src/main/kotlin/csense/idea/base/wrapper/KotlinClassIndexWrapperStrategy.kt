package csense.idea.base.wrapper

import com.intellij.openapi.project.Project
import com.intellij.psi.search.GlobalSearchScope
import csense.idea.base.bll.psiWrapper.`class`.KtPsiClass
import csense.idea.base.bll.psiWrapper.`class`.operations.asKtOrPsiClass
import csense.idea.base.csense.findByNameAndParameterTypes
import org.jetbrains.kotlin.psi.KtClassOrObject
import org.jetbrains.kotlin.psi.KtTypeAlias
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.companionObject
import kotlin.reflect.full.companionObjectInstance
import kotlin.reflect.full.functions


sealed interface KotlinClassIndexWrapperStrategy {

    fun resolveClassAndAlias(
        fqName: String,
        project: Project,
        globalSearchScope: GlobalSearchScope,
    ): List<KtPsiClass>

    class PreIdea2024 : KotlinClassIndexWrapperStrategy {
        private fun getPreIdea2024(): KFunction<*>? {
            TODO("Not yet implemented")
//            val kClass = Class.forName("org.jetbrains.kotlin.idea.stubindex.KotlinFullClassNameIndex").kotlin
//            val getInstance: KFunction<*>? = kClass.staticFunctions.firstOrNull { it.name == "getInstance" }
//            if (getInstance == null) {
//                return null
//            }
//            val instance: KotlinFullClassNameIndex = getInstance.call() as KotlinFullClassNameIndex
//            return instance::get
        }

        override fun resolveClassAndAlias(
            fqName: String,
            project: Project,
            globalSearchScope: GlobalSearchScope,
        ): List<KtPsiClass> {
            TODO("Not yet implemented")
        }
    }

    class PostIdea2024 : KotlinClassIndexWrapperStrategy {
        override fun resolveClassAndAlias(
            fqName: String,
            project: Project,
            globalSearchScope: GlobalSearchScope,
        ): List<KtPsiClass> {
            val classes: Collection<KtClassOrObject> = tryResolveViaClass(fqName, project, globalSearchScope)
            val aliases: Collection<KtTypeAlias> = tryResolveViaAlias(fqName, project, globalSearchScope)
            if (aliases.isEmpty()) {
                return resolveClasses(classes)
            }
            if (classes.isEmpty()) {
                return resolveAliases(aliases)
            }
            return emptyList()
        }

        private fun resolveClasses(classes: Collection<KtClassOrObject>): List<KtPsiClass> {
            return classes.map { it: KtClassOrObject ->
                it.asKtOrPsiClass()
            }
        }

        private fun resolveAliases(alias: Collection<KtTypeAlias>): List<KtPsiClass> {
            return alias.mapNotNull { it: KtTypeAlias ->
                it.asKtOrPsiClass()
            }
        }

        private fun resolveWithBoth(
            classes: Collection<KtClassOrObject>,
            alias: Collection<KtTypeAlias>,
        ): List<KtPsiClass> {
            //            else -> (classesPsi.asSequence() + typeAliasesPsi.asSequence()).minWithOrNull(Comparator { o1, o2 ->
//                scope.compare(o1.containingFile.virtualFile, o2.containingFile.virtualFile)
//            })
            return emptyList()
        }

        private fun tryResolveViaClass(
            fqName: String,
            project: Project,
            globalSearchScope: GlobalSearchScope,
        ): Collection<KtClassOrObject> {
            val method: KFunction<Collection<KtClassOrObject>> = kotlinFullClassNameIndexLookup() ?: return emptyList()
            return method.call(getKotlinFullClassNameIndexCompanionObject(), fqName, project, globalSearchScope)
        }

        private fun tryResolveViaAlias(
            fqName: String,
            project: Project,
            globalSearchScope: GlobalSearchScope,
        ): Collection<KtTypeAlias> {
            val method: KFunction<Collection<KtTypeAlias>> = kotlinTopLevelTypeAliasLookup() ?: return emptyList()
            return method.call(getKotlinTopLevelTypeAliasCompanionObject(), fqName, project, globalSearchScope)
        }

        private fun getKotlinFullClassNameIndexCompanionObject(): Any? {
            val kClass: KClass<out Any> =
                Class.forName("org.jetbrains.kotlin.idea.stubindex.KotlinFullClassNameIndex").kotlin
            return kClass.companionObjectInstance
        }

        private fun getKotlinTopLevelTypeAliasCompanionObject(): Any? {
            val kClass: KClass<out Any> =
                Class.forName("org.jetbrains.kotlin.idea.stubindex.KotlinTopLevelTypeAliasFqNameIndex").kotlin
            return kClass.companionObjectInstance
        }

        private fun kotlinFullClassNameIndexLookup(): KFunction<Collection<KtClassOrObject>>? {
            val kClass: KClass<out Any> =
                Class.forName("org.jetbrains.kotlin.idea.stubindex.KotlinFullClassNameIndex").kotlin
            val companion: KClass<*>? = kClass.companionObject
            if (companion == null || companion.simpleName != "Helper") {
                return null
            }
            return companion.functions.findByNameAndParameterTypes(
                name = "get",
                typesFqNames = arrayOf(
                    "org.jetbrains.kotlin.idea.stubindex.KotlinFullClassNameIndex\$Helper",
                    "java.lang.String",
                    "com.intellij.openapi.project.Project",
                    "com.intellij.psi.search.GlobalSearchScope"
                )
            ) as KFunction<Collection<KtClassOrObject>>?
        }

        private fun kotlinTopLevelTypeAliasLookup(): KFunction<Collection<KtTypeAlias>>? {
            val kClass: KClass<out Any> =
                Class.forName("org.jetbrains.kotlin.idea.stubindex.KotlinTopLevelTypeAliasFqNameIndex").kotlin
            val companion: KClass<*>? = kClass.companionObject
            if (companion == null || companion.simpleName != "Helper") {
                return null
            }
            return companion.functions.findByNameAndParameterTypes(
                name = "get",
                typesFqNames = arrayOf(
                    "org.jetbrains.kotlin.idea.stubindex.KotlinTopLevelTypeAliasFqNameIndex\$Helper",
                    "java.lang.String",
                    "com.intellij.openapi.project.Project",
                    "com.intellij.psi.search.GlobalSearchScope"
                )
            ) as KFunction<Collection<KtTypeAlias>>?
        }


    }
}