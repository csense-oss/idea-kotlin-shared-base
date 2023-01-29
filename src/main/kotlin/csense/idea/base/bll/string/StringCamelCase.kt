@file:Suppress("NOTHING_TO_INLINE")

package csense.idea.base.bll.string

import csense.kotlin.extensions.primitives.*

@JvmInline
value class CamelCase(val char: Char) {
    companion object
}

inline val Char.camelCase: CamelCase
    get() = CamelCase(this)

enum class CamelCaseBreakChar {
    UpperCase,
    LowerCase,
    Symbol
}

fun CamelCase.toCamelCaseBreakChar(): CamelCaseBreakChar = when {
    char.isLowerCaseLetter() -> CamelCaseBreakChar.LowerCase
    char.isUpperCaseLetter() -> CamelCaseBreakChar.UpperCase
    else -> CamelCaseBreakChar.Symbol
}

@JvmInline
value class StringCamelCase(val string: String) {
    companion object
}

val String.camelCase: StringCamelCase
    get() = StringCamelCase(this)

inline fun StringCamelCase.getBreakCharOrNull(index: Int): CamelCaseBreakChar? =
    string.getOrNull(index)?.camelCase?.toCamelCaseBreakChar()


//see tests for various (funny) cases
//example:

/**
 * Iterates over the [StringCamelCase.string] calling [onCamelCase] for each "word" that appears with different casing based
 * on the camelCase strategy
 * @param onCamelCase [kotlin.jvm.functions.Function2]<[Int], [String], [Unit]> will be called with the starting index of the given word
 * @example of Camel-Cases and what words are extracted
 *  - "RunTest"  -> ("R", "un", "Test")
 *  - "RUNTest" -> ("RUNT", "est")
 *  - "runTest" -> ("run", "Test")
 *  - "xNot" -> ("x", "Not")
 *  - "notX" -> ("not", "X")
 *  - "aAbB" -> ("a", "Ab", "B")
 */
inline fun StringCamelCase.forEachCamelCaseWord(
    onCamelCase: (startingIndex: Int, word: String) -> Unit
) {
    var currentBreakChar: CamelCaseBreakChar? = getBreakCharOrNull(index = 0)
    string.forEachSplitIndexed(
        shouldSplit = { index: Int, char: Char ->
            val shouldSplit = char.camelCase.toCamelCaseBreakChar() != currentBreakChar
            if (shouldSplit) {
                //rational: the second char in a string defines the type (eg, "Run" starts with uppercase, but the reset is lower)
                // while "RUN" is all uppercase. by looking at the second char you know what the expected case is for the rest of the string.
                currentBreakChar = getBreakCharOrNull(index = index + 1)
            }
            shouldSplit
        },
        onSplit = { startingIndex: Int, word: String ->
            onCamelCase(startingIndex, word)
        }
    )
}

//TODO csense kotlin
inline fun String.forEachSplitIndexed(
    shouldSplit: (index: Int, char: Char) -> Boolean,
    onSplit: (startingIndex: Int, split: String) -> Unit
) {
    if (isEmpty()) {
        return
    }
    val splitContainer = StringSplitIndexedContainer(
        initialCapacityOf = this
    )
    splitContainer.forEachSplitOnString(
        string = this,
        shouldSplit = shouldSplit,
        onSplit = onSplit
    )
}

//TODO csense kotlin
//TODO internal? hmm..
//Todo name etc?? hmm...
class StringSplitIndexedContainer(
    capacity: Int = 100
) {
    constructor(initialCapacityOf: String) : this(initialCapacityOf.length)

    private var startingIndex: Int = 0

    private val currentStringBuilder: StringBuilder = StringBuilder(capacity)

    fun append(char: Char) {
        currentStringBuilder.append(char)
    }

    fun split(): Pair<Int, String> {
        val splitString = currentStringBuilder.toStringAndClear()
        return Pair(startingIndex, splitString).also {
            startingIndex += splitString.length
        }
    }


    fun isNotEmpty(): Boolean =
        !isEmpty()

    private fun isEmpty(): Boolean =
        currentStringBuilder.isEmpty()

    inline fun forEachSplitOnString(
        string: String,
        shouldSplit: (index: Int, char: Char) -> Boolean,
        onSplit: (startingIndex: Int, split: String) -> Unit
    ) {
        string.forEachIndexed { index: Int, char: Char ->
            if (shouldSplit(index, char)) {
                splitWithCallback(onSplit)
            }
            append(char)
        }
        splitWithCallbackIfNotEmpty(onSplit)
    }

    inline fun splitWithCallbackIfNotEmpty(
        callback: (startingIndex: Int, split: String) -> Unit
    ) {
        if (isNotEmpty()) {
            splitWithCallback(callback)
        }
    }

    inline fun splitWithCallback(
        callback: (startingIndex: Int, split: String) -> Unit
    ) {
        val (startingIndex: Int, string: String) = split()
        callback(startingIndex, string)
    }

}
//TODO csense kotlin
/**
 * gets the current content ([toString]) of this [StringBuilder] and [clear]s it
 *
 */
fun StringBuilder.toStringAndClear(): String = toString().also {
    clear()
}
