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
    }
}


