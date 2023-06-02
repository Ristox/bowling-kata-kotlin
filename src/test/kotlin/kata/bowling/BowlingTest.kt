package kata.bowling

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class BowlingTest {

    lateinit var game: Bowling

    @BeforeEach
    fun setup() {
        game = Bowling()
    }

    @Test
    fun worstGame() {
        for (i in 0..19) {
            game.roll(0)
        }

        val score = game.score()

        assertThat(score)
            .isEqualTo(0)
    }

    @Test
    fun returnsOne_forOnePinRolled() {
        game.roll(1)
        for (i in 1..19) {
            game.roll(0)
        }

        val score = game.score()

        assertThat(score)
            .isEqualTo(1)
    }

    @Test
    fun throwsIllegalArgument_whenRolledWithPinsMoreThanTen() {
        val test: () -> Unit = { game.roll(11) }

        assertThatIllegalArgumentException()
            .isThrownBy(test)
    }

    @Test
    fun throwsIllegalArgument_whenRolledWithNegativePins() {
        val test: () -> Unit = { game.roll(-1) }

        assertThatIllegalArgumentException()
            .isThrownBy(test)
    }

    @Test
    fun spare() {
        roll(5, 5, 1)

        rollZeros(17)

        assertThat(game.score()).isEqualTo(12)
    }

    @Test
    fun twoSpares() {
        roll(5, 5, 5, 5, 1)

        rollZeros(15)

        assertThat(game.score()).isEqualTo(15+11+1)
    }

    @Test
    fun nonSpare() {
        roll(0, 5, 5, 1)

        rollZeros(16)

        assertThat(game.score()).isEqualTo(11)
    }

    @Test
    fun strike() {
        roll(10, 1, 1)

        rollZeros(16)

        assertThat(game.score()).isEqualTo(12+2)
    }

    @Test
    fun godGame() {
        for (i in 0 until 12) {
            game.roll(10)
        }

        assertThat(game.score()).isEqualTo(300)
    }

    private fun roll(vararg i: Int) {
        i.iterator().forEachRemaining {
            game.roll(it)
        }
    }

    private fun rollZeros(rolls: Int) {
        for (i in 1..rolls) {
            game.roll(0)
        }
    }
}
