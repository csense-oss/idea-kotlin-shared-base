package csense.idea.base.bll

import com.intellij.openapi.progress.*

@Throws(Throwable::class)
public inline fun <R> tryAndIgnoreProcessCanceledException(
    action: () -> R
): R? {
    try {
        return action()
    } catch (exception: ProcessCanceledException) {
        return null
    } catch (other: Throwable) {
        if (other.cause is ProcessCanceledException) {
            return null
        }
        throw other
    }
}