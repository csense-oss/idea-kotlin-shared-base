package csense.idea.base.bll.psiWrapper.`class`.operations

import com.intellij.psi.*
import csense.idea.base.bll.kotlin.*
import csense.idea.base.bll.psiWrapper.`class`.*
import org.jetbrains.kotlin.psi.*


inline fun KtPsiClass.forEachSuperClassType(action: (KtPsiClass) -> Unit): Unit = when (this) {
    is KtPsiClass.Kt -> forEachSuperClassType(action)
    is KtPsiClass.Psi -> forEachSuperClassType(action)
}

inline fun KtPsiClass.Kt.forEachSuperClassType(action: (KtPsiClass) -> Unit) {
    ktClassOrObject.forEachSuperClassType {
        action(KtPsiClass.Kt(it))
    }
}

inline fun KtPsiClass.Psi.forEachSuperClassType(action: (KtPsiClass) -> Unit) {
    psiClass.forEachSuperClassType {
        action(KtPsiClass.Psi(it))
    }
}

//TODO move
inline fun KtClassOrObject.forEachSuperClassType(onEachSuperType: (KtClassOrObject) -> Unit) {
    var current = this.superClass
    while (current != null) {
        onEachSuperType(current)
        current = current.superClass
    }
}

//TODO move
inline fun PsiClass.forEachSuperClassType(onEachSuperType: (PsiClass) -> Unit) {
    var current = this.superClass
    while (current != null) {
        onEachSuperType(current)
        current = current.superClass
    }
}


