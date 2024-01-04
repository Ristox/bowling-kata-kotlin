package ee.rsx.kata.codurance.shoppingcart.inventory

import ee.rsx.kata.codurance.shoppingcart.rate.TaxRate.FIRST_NECESSITY
import ee.rsx.kata.codurance.shoppingcart.rate.TaxRate.NORMAL
import java.lang.IllegalStateException
import java.math.BigDecimal

class Inventory {

  private val products = mapOf(
    "Iceberg" to InventoryItem(
      name = "Iceberg", symbol = "\uD83E\uDD6C", revenuePercentage = 15, taxRate = NORMAL, cost = BigDecimal("1.55")
    ),
    "Tomato" to InventoryItem(
      name = "Tomato", symbol = "\uD83C\uDF45", revenuePercentage = 15, taxRate = NORMAL, cost = BigDecimal("0.52")
    ),
    "Chicken" to InventoryItem(
      name = "Chicken", symbol = "\uD83C\uDF57", revenuePercentage = 12, taxRate = NORMAL, cost = BigDecimal("1.34")
    ),
    "Bread" to InventoryItem(
      name = "Bread", symbol = "\uD83C\uDF5E", cost = BigDecimal("0.71"), revenuePercentage = 12, taxRate = FIRST_NECESSITY
    ),
    "Corn" to InventoryItem(
      name = "Corn", symbol = "\uD83C\uDF3D", cost = BigDecimal("1.21"), revenuePercentage = 12, taxRate = FIRST_NECESSITY
    )
  )

  fun iceberg() = productInstanceOf("Iceberg")

  fun tomato() = productInstanceOf("Tomato")

  fun chicken() = productInstanceOf("Chicken")

  fun bread() = productInstanceOf("Bread")

  fun corn() = productInstanceOf("Corn")

  private fun productInstanceOf(name: String) =
    products[name]?.toProductInstance() ?: throw IllegalStateException("No such product ($name) found in inventory")
}
