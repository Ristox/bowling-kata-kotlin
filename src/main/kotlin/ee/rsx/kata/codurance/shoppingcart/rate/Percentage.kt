package ee.rsx.kata.codurance.shoppingcart.rate

import java.math.BigDecimal
import java.math.RoundingMode

data class Percentage(val rate: Int) {

  companion object {
    fun of(value: Int) = Percentage(value)
  }

  init {
    require(rate in 0..100) { "Percentage rate $rate is not within allowed range (0%..100%)" }
  }

  operator fun invoke() = rate

  fun addTo(value: BigDecimal): BigDecimal = value.multiply(asIncreaseFraction()).setScale(2, RoundingMode.UP)

  fun asReduceFraction(): BigDecimal = BigDecimal(100 - this()).divide(BigDecimal(100))

  private fun asIncreaseFraction(): BigDecimal = BigDecimal(100 + this()).divide(BigDecimal(100))
}
