package ee.rsx.kata.codurance.shoppingcart.cart

import ee.rsx.kata.codurance.shoppingcart.rate.Percentage

enum class DiscountCoupon(val percentage: Percentage) {
  PROMO_5(Percentage(rate = 5)),
  PROMO_10(Percentage(rate = 10))
}
