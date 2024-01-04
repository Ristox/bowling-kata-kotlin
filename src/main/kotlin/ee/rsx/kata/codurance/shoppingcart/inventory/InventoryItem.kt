package ee.rsx.kata.codurance.shoppingcart.inventory

import ee.rsx.kata.codurance.shoppingcart.rate.Percentage
import ee.rsx.kata.codurance.shoppingcart.rate.TaxRate
import ee.rsx.kata.codurance.shoppingcart.product.Product
import java.math.BigDecimal

data class InventoryItem(
  val name: String,
  val symbol: String,
  val cost: BigDecimal,
  val revenuePercentage: Int,
  val taxRate: TaxRate,
  val id: Int? = null
) {
  fun toProductInstance() = Product(name, symbol, netPrice, fullPrice)
  
  private val netPrice: BigDecimal = Percentage(revenuePercentage).addTo(cost)

  private val fullPrice: BigDecimal = taxRate.percentage.addTo(netPrice)
}
