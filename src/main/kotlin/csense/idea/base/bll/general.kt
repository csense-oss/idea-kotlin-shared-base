package csense.idea.base.bll

import com.intellij.openapi.progress.*


fun <R> tryAndIgnoreProcessCanceledException(
    action: () -> R?
): R? {
    return try {
        action()
    } catch (_: ProcessCanceledException) {
        null
    }
}