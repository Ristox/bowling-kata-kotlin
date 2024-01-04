package ee.rsx.kata.codurance.shoppingcart.cart

import ee.rsx.kata.codurance.shoppingcart.product.Product
import java.math.BigDecimal
import java.math.BigInteger.ONE
import java.math.RoundingMode

/**
  https://www.codurance.com/katas/shopping-cart-kata
 **/

class ShoppingCart {

  private val cartItems: MutableList<CartItem> = mutableListOf()

  private val discounts: MutableSet<DiscountCoupon> = mutableSetOf()

  val items: List<CartItem>
    get() = cartItems

  val totalItemsCount: Int
    get() = cartItems.size

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

  val totalPricePrinted
    get() = "$totalPrice €"

  fun add(vararg products: Product) = cartItems.addAll(products.map { CartItem(it) })

  fun countOf(product: Product) = cartItems.count { it.product == product }

  fun applyDiscount(coupon: DiscountCoupon) {
    discounts.add(coupon)
  }
}
