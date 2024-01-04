package ee.rsx.kata.codurance.shoppingcart.product

import java.math.BigDecimal

data class Product(
  val name: String,
  val symbol: String,
  val netPrice: BigDecimal,
  val fullPrice: BigDecimal
)

