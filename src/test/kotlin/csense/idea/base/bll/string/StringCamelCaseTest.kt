package csense.idea.base.bll.string


import csense.kotlin.tests.assertions.*
import org.junit.*

class StringCamelCaseTest {
    class ForEachCamelCase {

        @Test
        fun empty() {
            "".camelCase.forEachCamelCaseWord { _, _ -> shouldNotBeCalled() }
        }

        @Test
        fun singleLowercase() {
            val testData = listOf(
                "a" to 0
            )
            assertForeachCamelCaseCalledWith(
                expectedOrder = testData,
                forInput = "a"
            )
        }

        @Test
        fun singleUppercase() {
            val testData = listOf(
                "A" to 0
            )
            assertForeachCamelCaseCalledWith(
                expectedOrder = testData,
                forInput = "A"
            )
        }

        @Test
        fun multipleAllUppercase() {
            val testData = listOf(
                "ABC" to 0
            )
            assertForeachCamelCaseCalledWith(
                expectedOrder = testData,
                forInput = "ABC"
            )
        }

        @Test
        fun multipleAllLowercase() {
            val testData = listOf(
                "abc" to 0
            )
            assertForeachCamelCaseCalledWith(
                expectedOrder = testData,
                forInput = "abc"
            )
        }

        @Test
        fun multipleWordsStartLowerCase() {
            val testData = listOf(
                "abc" to 0,
                "To" to 3
            )
            assertForeachCamelCaseCalledWith(
                expectedOrder = testData,
                forInput = "abcTo"
            )
        }

        @Test
        fun multipleWordsStartUpperCase() {
            val testData = listOf(
                "ABC" to 0,
                "to" to 3
            )
            assertForeachCamelCaseCalledWith(
                expectedOrder = testData,
                forInput = "ABCto"
            )
        }

        @Test
        fun mixed() {
            val testData = listOf(
                "abc" to 0,
                "TO" to 3,
                "qw" to 5
            )
            assertForeachCamelCaseCalledWith(
                expectedOrder = testData,
                forInput = "abcTOqw"
            )
        }

        @Test
        fun shouldRespectNumberSequences() {
            val testData = listOf(
                "1234" to 0
            )
            assertForeachCamelCaseCalledWith(
                expectedOrder = testData,
                forInput = "1234"
            )
        }

        @Test
        fun shouldRespectNumberSequencesAndStrings() {
            val testData = listOf(
                "1234" to 0,
                "Abc" to 4
            )
            assertForeachCamelCaseCalledWith(
                expectedOrder = testData,
                forInput = "1234Abc"
            )
        }

        @Test
        fun shouldRecognizeSingleLetterStart() {
            val testData = listOf(
                "x" to 0,
                "Not" to 1,
            )
            assertForeachCamelCaseCalledWith(
                expectedOrder = testData,
                forInput = "xNot"
            )
        }

        @Test
        fun shouldRespectNumbersInStrings() {
            val testData = listOf(
                "QWE" to 0,
                "1234" to 3,
                "Abc" to 7
            )
            assertForeachCamelCaseCalledWith(
                expectedOrder = testData,
                forInput = "QWE1234Abc"
            )
        }

        @Test
        fun endsSingleDifferentCase() {
            val testData = listOf(
                "qwe" to 0,
                "A" to 3,
            )
            assertForeachCamelCaseCalledWith(
                expectedOrder = testData,
                forInput = "qweA"
            )
        }


        //showcasing that a letter in the middle will yield different result than "expected"
        // (expected is "is", "A", "Car" if perceived as a human).
        @Test
        fun singleLetterInTheMiddleAreNotSupported() {
            val testData = listOf(
                "is" to 0,
                "AC" to 2,
                "ar" to 4
            )
            assertForeachCamelCaseCalledWith(
                expectedOrder = testData,
                forInput = "isACar"
            )
        }

        //showcasing that a combination of single letters are unsupported (in the middle, at the ends they are)
        // (potentially expected as "a", "A", "b", "B" if perceived as a human).
        @Test
        fun onlySingleLettersAreNotSupportedInTheMiddle() {
            val testData = listOf(
                "a" to 0,
                "Ab" to 1,
                "B" to 3,
            )
            assertForeachCamelCaseCalledWith(
                expectedOrder = testData,
                forInput = "aAbB"
            )
        }
        @Test
        fun camelCase(){
            val testData = listOf(
                "not" to 0,
                "Cases" to 3
            )
            assertForeachCamelCaseCalledWith(
                expectedOrder = testData,
                forInput = "notCases"
            )
        }

        private fun assertForeachCamelCaseCalledWith(
            expectedOrder: List<Pair<String, Int>>,
            forInput: String
        ): Unit = assertCallbackCalledWith(
            expectedItemsInOrder = expectedOrder,
            assertFunction = { lhs, rhs ->
                lhs == rhs
            },
            testCode = { expected ->
                forInput.camelCase.forEachCamelCaseWord { firstIndex, string ->
                    expected(Pair(string, firstIndex))
                }
            }
        )
    }

    @Test
    fun charToCamelCaseBreakChar() {
        'a'.camelCase.toCamelCaseBreakChar().assert(CamelCaseBreakChar.LowerCase)
        'A'.camelCase.toCamelCaseBreakChar().assert(CamelCaseBreakChar.UpperCase)
        '1'.camelCase.toCamelCaseBreakChar().assert(CamelCaseBreakChar.Symbol)
    }
}
