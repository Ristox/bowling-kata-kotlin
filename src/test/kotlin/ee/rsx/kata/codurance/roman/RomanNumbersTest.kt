package ee.rsx.kata.codurance.roman

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class RomanNumbersTest {

    @ParameterizedTest
    @MethodSource("baseValues")
    fun `arabic numbers 1-10 correspond to expected Roman numbers`(value: Int, expectedRomanLiteral: String) {
        val roman = toRoman(value)

        assertThat(roman).isEqualTo(expectedRomanLiteral)
    }

    @Test
    fun `throws IllegalArgumentException, when provided arabic number is 0`() {
        val test: () -> Unit = { toRoman(0) }

        assertThrows<IllegalArgumentException>(test).run {
            assertThat(message).isEqualTo("Value must be positive integer")
        }
    }

    @ParameterizedTest
    @MethodSource("teenValues")
    fun `converts arabic numbers 11-20 to expected Roman numbers`(value: Int, expectedRomanLiteral: String) {
        val roman = toRoman(value)

        assertThat(roman).isEqualTo(expectedRomanLiteral)
    }

    @ParameterizedTest
    @MethodSource("twentiesValues")
    fun `converts arabic numbers 21-30 to expected Roman numbers`(value: Int, expectedRomanLiteral: String) {
        val roman = toRoman(value)

        assertThat(roman).isEqualTo(expectedRomanLiteral)
    }

    @ParameterizedTest
    @MethodSource("thirtiesValues")
    fun `converts arabic numbers 31-39 to expected Roman numbers`(value: Int, expectedRomanLiteral: String) {
        val roman = toRoman(value)

        assertThat(roman).isEqualTo(expectedRomanLiteral)
    }

    companion object {
        @JvmStatic
        fun baseValues(): Stream<Arguments> = Stream.of(
            Arguments.of(1, "I"),
            Arguments.of(2, "II"),
            Arguments.of(3, "III"),
            Arguments.of(4, "IV"),
            Arguments.of(5, "V"),
            Arguments.of(6, "VI"),
            Arguments.of(7, "VII"),
            Arguments.of(8, "VIII"),
            Arguments.of(9, "IX"),
            Arguments.of(10, "X")
        )

        @JvmStatic
        fun teenValues(): Stream<Arguments> = Stream.of(
            Arguments.of(11, "XI"),
            Arguments.of(12, "XII"),
            Arguments.of(13, "XIII"),
            Arguments.of(14, "XIV"),
            Arguments.of(15, "XV"),
            Arguments.of(16, "XVI"),
            Arguments.of(17, "XVII"),
            Arguments.of(18, "XVIII"),
            Arguments.of(19, "XIX"),
            Arguments.of(20, "XX")
        )

        @JvmStatic
        fun twentiesValues(): Stream<Arguments> = Stream.of(
            Arguments.of(21, "XXI"),
            Arguments.of(22, "XXII"),
            Arguments.of(23, "XXIII"),
            Arguments.of(24, "XXIV"),
            Arguments.of(25, "XXV"),
            Arguments.of(26, "XXVI"),
            Arguments.of(27, "XXVII"),
            Arguments.of(28, "XXVIII"),
            Arguments.of(29, "XXIX"),
            Arguments.of(30, "XXX")
        )

        @JvmStatic
        fun thirtiesValues(): Stream<Arguments> = Stream.of(
            Arguments.of(31, "XXXI"),
            Arguments.of(32, "XXXII"),
            Arguments.of(33, "XXXIII"),
            Arguments.of(34, "XXXIV"),
            Arguments.of(35, "XXXV"),
            Arguments.of(36, "XXXVI"),
            Arguments.of(37, "XXXVII"),
            Arguments.of(38, "XXXVIII"),
            Arguments.of(39, "XXXIX")
        )
    }
}


