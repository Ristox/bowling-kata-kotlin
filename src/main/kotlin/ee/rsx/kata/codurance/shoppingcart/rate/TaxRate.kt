package ee.rsx.kata.codurance.shoppingcart.rate

enum class TaxRate(val percentage: Percentage) {
  NORMAL(Percentage(rate = 21)),
  FIRST_NECESSITY(Percentage(rate = 10))
}
