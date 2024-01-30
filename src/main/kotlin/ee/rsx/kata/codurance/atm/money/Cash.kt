package ee.rsx.kata.codurance.atm.money

data class Cash(
  val nomination: Int,
  val type: CashType
)

enum class CashType {
  COIN,
  BILL
}
