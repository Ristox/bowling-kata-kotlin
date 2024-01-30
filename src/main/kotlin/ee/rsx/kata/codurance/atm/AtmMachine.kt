package ee.rsx.kata.codurance.atm

import ee.rsx.kata.codurance.atm.money.Cash
import ee.rsx.kata.codurance.atm.money.CashType.BILL
import ee.rsx.kata.codurance.atm.money.CashType.COIN

class AtmMachine {

    fun withdraw(amount: Int): List<Cash> {
        var remaining = amount
        val withdrawal: MutableList<Cash> = mutableListOf()
        cashInDescendingNominations.forEach { cash ->
            val modulus = remaining.mod(cash.nomination)
            if (modulus != remaining) {
                val amount = remaining - modulus
                val howMany = amount / cash.nomination
                repeat(howMany) {
                    withdrawal.add(cash.copy())
                }
                remaining -= amount
            }
        }
        return withdrawal
    }

    private val cashInDescendingNominations =
        listOf(
            Cash(100, BILL),
            Cash(10, BILL),
            Cash(50, BILL),
            Cash(20, BILL),
            Cash(200, BILL),
            Cash(5, BILL),
            Cash(500, BILL),
            Cash(2, COIN),
            Cash(1, COIN),
        ).sortedByDescending { it.nomination }
}
