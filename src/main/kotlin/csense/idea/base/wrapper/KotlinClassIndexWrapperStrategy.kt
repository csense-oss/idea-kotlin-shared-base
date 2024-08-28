package csense.idea.base.wrapper

import com.intellij.openapi.project.*
import com.intellij.psi.search.*
import csense.idea.base.bll.psiWrapper.`class`.*
import csense.idea.base.bll.psiWrapper.`class`.operations.*
import csense.idea.base.csense.*
import org.jetbrains.kotlin.psi.*
import kotlin.reflect.*
import kotlin.reflect.full.*


sealed interface KotlinClassIndexWrapperStrategy {

    fun resolveClassAndAlias(
        fqName: String,
        project: Project,
        globalSearchScope: GlobalSearchScope,
    ): List<KtPsiClass>

    class PreIdea2024 : KotlinClassIndexWrapperStrategy {

        override fun resolveClassAndAlias(
            fqName: String,
            project: Project,
            scope: GlobalSearchScope,
        ): List<KtPsiClass> {
            val classes: Collection<KtClassOrObject> = tryResolveViaClass(
                fqName = fqName,
                project = project,
                globalSearchScope = scope
            )
            val aliases: Collection<KtTypeAlias> = tryResolveViaAlias(
                fqName = fqName,
                project = project,
                globalSearchScope = scope
            )

            return KotlinClassAndTypeAliasResolver.resolve(
                classes = classes,
                aliases = aliases
            )
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
            return kClass.objectInstance
        }

        private fun getKotlinTopLevelTypeAliasCompanionObject(): Any? {
            val kClass: KClass<out Any> =
                Class.forName("org.jetbrains.kotlin.idea.stubindex.KotlinTopLevelTypeAliasFqNameIndex").kotlin
            return kClass.objectInstance
        }

        private fun kotlinFullClassNameIndexLookup(): KFunction<Collection<KtClassOrObject>>? {
            val kClass: KClass<out Any> =
                Class.forName("org.jetbrains.kotlin.idea.stubindex.KotlinFullClassNameIndex").kotlin
            return kClass.functions.findByNameAndParameterTypes(
                name = "get",
                typesFqNames = arrayOf(
                    "org.jetbrains.kotlin.idea.stubindex.KotlinFullClassNameIndex",
                    "java.lang.String",
                    "com.intellij.openapi.project.Project",
                    "com.intellij.psi.search.GlobalSearchScope"
                )
            ) as KFunction<Collection<KtClassOrObject>>?
        }

        private fun kotlinTopLevelTypeAliasLookup(): KFunction<Collection<KtTypeAlias>>? {
            val kClass: KClass<out Any> =
                Class.forName("org.jetbrains.kotlin.idea.stubindex.KotlinTopLevelTypeAliasFqNameIndex").kotlin
            return kClass.functions.findByNameAndParameterTypes(
                name = "get",
                typesFqNames = arrayOf(
                    "org.jetbrains.kotlin.idea.stubindex.KotlinTopLevelTypeAliasFqNameIndex",
                    "java.lang.String",
                    "com.intellij.openapi.project.Project",
                    "com.intellij.psi.search.GlobalSearchScope"
                )
            ) as KFunction<Collection<KtTypeAlias>>?
        }

    }

    class PostIdea2024 : KotlinClassIndexWrapperStrategy {
        override fun resolveClassAndAlias(
            fqName: String,
            project: Project,
            scope: GlobalSearchScope,
        ): List<KtPsiClass> {
            val classes: Collection<KtClassOrObject> = tryResolveViaClass(
                fqName = fqName,
                project = project,
                globalSearchScope = scope
            )
            val aliases: Collection<KtTypeAlias> = tryResolveViaAlias(
                fqName = fqName,
                project = project,
                globalSearchScope = scope
            )

            return KotlinClassAndTypeAliasResolver.resolve(
                classes = classes,
                aliases = aliases
            )
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

object KotlinClassAndTypeAliasResolver {
    fun resolve(classes: Collection<KtClassOrObject>, aliases: Collection<KtTypeAlias>): List<KtPsiClass> {
        if (aliases.isEmpty()) {
            return resolveClasses(classes)
        }

        if (classes.isEmpty()) {
            return resolveAliases(aliases)
        }

        return resolveWithBoth(
            classes = classes,
            alias = aliases
        )
    }


    private fun resolveClasses(classes: Collection<KtClassOrObject>): List<KtPsiClass> {
        return classes.map { it: KtClassOrObject ->
            it.asKtOrPsiClass()
        }
    }

    private fun resolveAliases(alias: Collection<KtTypeAlias>): List<KtPsiClass> {
        return alias.mapNotNull { it: KtTypeAlias ->
            it.resolveFirstClassType2()
        }
    }

    private fun resolveWithBoth(
        classes: Collection<KtClassOrObject>,
        alias: Collection<KtTypeAlias>,
    ): List<KtPsiClass> {
        val zipped: List<Pair<KtClassOrObject, KtTypeAlias>> = classes.zip(alias)
        //TODO use sortedByFalseFirst (csense)
        val sorted: List<Pair<KtClassOrObject, KtTypeAlias>> =
            zipped.sortedBy { it: Pair<KtClassOrObject, KtTypeAlias> ->
                it.second.containingFile.virtualFile == null
            }
        return sorted.map { it: Pair<KtClassOrObject, KtTypeAlias> ->
            KtPsiClass.Kt(ktClassOrObject = it.first, typeAlias = it.second)
        }
    }


}