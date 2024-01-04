package ee.rsx.kata.codurance.shoppingcart.cart

import ee.rsx.kata.codurance.shoppingcart.inventory.Inventory
import ee.rsx.kata.codurance.shoppingcart.product.Product
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.math.BigDecimal

/**
  https://www.codurance.com/katas/shopping-cart-kata
 **/

class ShoppingCartTest {

  @Nested
  @DisplayName("Empty shopping cart")
  inner class EmptyShoppingCart {

    @Test
    fun `has total product items as 0`() {
      val cart = ShoppingCart()

      assertThat(cart.totalItemsCount).isEqualTo(0)
    }

    @Test
    fun `has total price of 0,00`() {
      val cart = ShoppingCart()

      assertThat(cart.totalPrice).isEqualTo(BigDecimal("0.00"))
    }

    @Test
    fun `has total price printed as 0,00 €`() {
      val cart = ShoppingCart()

      assertThat(cart.totalPricePrinted).isEqualTo("0.00 €")
    }
  }

  @Nested
  @DisplayName("Adding items to shopping cart")
  inner class AddingItemsToShoppingCart {

    private lateinit var inventory: Inventory


    @BeforeEach
    fun setup() {
      inventory = Inventory()
    }

    @Test
    fun `adds Iceberg to cart as item with expected product, unit price and total price`() {
      ShoppingCart().run {
        val iceberg = inventory.iceberg()

        add(iceberg)

        items.assertContainsSingleProduct(
          product = iceberg,
          withNetPrice = "1.79",
          withTotalPrice = "2.17"
        )
      }
    }

    @Test
    fun `adds Tomato to cart as item with expected product, unit price and total price`() {
      ShoppingCart().run {
        val tomato = inventory.tomato()

        add(tomato)

        items.assertContainsSingleProduct(
          product = tomato,
          withNetPrice = "0.60",
          withTotalPrice = "0.73"
        )
      }
    }

    @Test
    fun `adds Chicken to cart as item with expected product, unit price and total price`() {
      ShoppingCart().run {
        val chicken = inventory.chicken()

        add(chicken)

        items.assertContainsSingleProduct(
          product = chicken,
          withNetPrice = "1.51",
          withTotalPrice = "1.83"
        )
      }
    }

    @Test
    fun `adds Bread to cart as item with expected product, unit price and total price`() {
      ShoppingCart().run {
        val bread = inventory.bread()

        add(bread)

        items.assertContainsSingleProduct(
          product = bread,
          withNetPrice = "0.80",
          withTotalPrice = "0.88"
        )
      }
    }

    @Test
    fun `adds Corn to cart as item with expected product, unit price and total price`() {
      ShoppingCart().run {
        val corn = inventory.corn()

        add(corn)

        items.assertContainsSingleProduct(
          product = corn,
          withNetPrice = "1.36",
          withTotalPrice = "1.50"
        )
      }
    }

    private fun List<CartItem>.assertContainsSingleProduct(
      product: Product, withNetPrice: String, withTotalPrice: String
    ) {
      assertThat(this).containsExactly(CartItem(product))
      with(first().product) {
        assertThat(netPrice).isEqualTo(BigDecimal(withNetPrice))
        assertThat(fullPrice).isEqualTo(BigDecimal(withTotalPrice))
      }
    }

    @Test
    fun `adds all product items to cart once, with expected total products count and total price`() {
      ShoppingCart().run {
        add(inventory.iceberg(), inventory.tomato(), inventory.chicken(), inventory.bread(), inventory.corn())

        assertThat(totalItemsCount).isEqualTo(5)
        assertThat(totalPrice).isEqualTo(BigDecimal("7.11"))
      }
    }

    @Nested
    @DisplayName("Filling cart with different product items in different quantities")
    inner class AddingDifferentProductItemsToCartMoreThanOnce {

      private lateinit var iceberg: Product
      private lateinit var tomato: Product
      private lateinit var bread: Product
      private lateinit var chicken: Product
      private lateinit var corn: Product

      @BeforeEach
      fun setup() {
        iceberg = inventory.iceberg()
        tomato = inventory.tomato()
        bread = inventory.bread()
        chicken = inventory.chicken()
        corn = inventory.corn()
      }

      private fun filledShoppingCart() = ShoppingCart().apply {
        add(
          iceberg,
          iceberg,
          iceberg,
          tomato,
          chicken,
          bread,
          bread,
          corn
        )
      }

      @Test
      fun `counts product quantities correctly`() {
        filledShoppingCart().run {
          mapOf(
            iceberg to 3,
            tomato to 1,
            chicken to 1,
            bread to 2,
            corn to 1
          )
            .forEach { (product, quantity) ->
              assertThat(
                countOf(product)
              ).isEqualTo(quantity)
            }
        }
      }

      @Test
      fun `counts items correctly`() {
        filledShoppingCart().run {
          assertThat(totalItemsCount).isEqualTo(8)
        }
      }

      @Test
      fun `calculates cart total price correctly`() {
        filledShoppingCart().run {
          assertThat(totalPrice).isEqualTo(BigDecimal("12.33"))
        }
      }

      @Test
      fun `applies discount coupon to cart, reducing total price correctly`() {
        filledShoppingCart().run {
          applyDiscount(DiscountCoupon.PROMO_5)

          assertThat(totalPrice).isEqualTo(BigDecimal("11.71"))
        }
      }

      @Test
      fun `only applies same discount coupon once to cart, reducing total price correctly just once`() {
        filledShoppingCart().run {
          applyDiscount(DiscountCoupon.PROMO_5)
          applyDiscount(DiscountCoupon.PROMO_5)

          assertThat(totalPrice).isEqualTo(BigDecimal("11.71"))
        }
      }

      @Test
      fun `applies all different discount coupons to cart, reducing total price correctly`() {
        filledShoppingCart().run {
          applyDiscount(DiscountCoupon.PROMO_5)
          applyDiscount(DiscountCoupon.PROMO_10)

          assertThat(totalPrice).isEqualTo(BigDecimal("10.54"))
        }
      }
    }
  }
}
