package ee.rsx.kata.codurance.shoppingcart.cart

import ee.rsx.kata.codurance.shoppingcart.product.Product
import java.math.BigDecimal
import java.math.BigInteger.ONE
import java.math.RoundingMode

class ShoppingCart {

  private val discounts: MutableSet<DiscountCoupon> = mutableSetOf()

  val totalPrice: BigDecimal
    get() = cartItems.sumOf { it.product.fullPrice }
      .multiply(
        discounts
          .map { it.percentage.asReduceFraction() }
          .ifEmpty { listOf(BigDecimal(ONE)) }
          .reduce { firstReduce, subsequentReduce ->
            firstReduce.multiply(subsequentReduce)
          }
      )
      .setScale(2, RoundingMode.HALF_UP)

  fun add(vararg products: Product) = cartItems.addAll(products.map { CartItem(it) })

  val totalPricePrinted
    get() = "$totalPrice €"

  fun countOf(product: Product) = cartItems.count { it.product == product }

  fun applyDiscount(coupon: DiscountCoupon) {
    discounts.add(coupon)
  }

  val items: List<CartItem>
    get() = cartItems

  private val cartItems: MutableList<CartItem> = mutableListOf()

  val totalItemsCount: Int
    get() = cartItems.size
}
