package ee.rsx.kata.codurance.atm

import ee.rsx.kata.codurance.atm.money.Note
import ee.rsx.kata.codurance.atm.money.Note.*
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test

class AtmMachineTest {

    @Test
    fun `withdraw funds of 434 Euros`() {
        val atm = AtmMachine()

        val withdrawal = atm.withdraw(434)

        assertThat(withdrawal.sumOf { it.nomination }).isEqualTo(434)
        assertThat(withdrawal)
            .containsExactlyInAnyOrder(
                BILL_200,
                BILL_200,
                BILL_20,
                BILL_10,
                COIN_2,
                COIN_2
            )
    }

    @Test
    fun `withdraw funds of 1397 Euros`() {
        val atm = AtmMachine()

        val withdrawal = atm.withdraw(1397)

        assertThat(withdrawal.sumOf { it.nomination }).isEqualTo(1397)
        assertThat(withdrawal)
            .containsExactlyInAnyOrder(
                BILL_500,
                BILL_500,
                BILL_200,
                BILL_100,
                BILL_50,
                BILL_20,
                BILL_20,
                BILL_5,
                COIN_2,
            )
    }

    @Test
    fun `withdraw funds of 1725 Euros, from limited funds of ATM`() {
        val atm = AtmMachine(limitedFunds = true)
        val initialBalance = atm.remainingBalance

        val withdrawal = atm.withdraw(1725)

        assertThat(withdrawal.sumOf { it.nomination }).isEqualTo(1725)
        assertThat(withdrawal)
            .containsExactlyInAnyOrder(
                BILL_500,
                BILL_500,
                BILL_200,
                BILL_200,
                BILL_200,
                BILL_100,
                BILL_20,
                BILL_5
            )

        val expectedRemainingBalance = initialBalance - 1725
        assertThat(atm.remainingBalance).isEqualTo(expectedRemainingBalance)

        val remainingFunds = atm.remainingFunds
        remainingFunds.assertHas(0, BILL_500)
        remainingFunds.assertHas(0, BILL_200)
        remainingFunds.assertHas(4, BILL_100)
        remainingFunds.assertHas(12, BILL_50)
        remainingFunds.assertHas(19, BILL_20)
        remainingFunds.assertHas(50, BILL_10)
        remainingFunds.assertHas(99, BILL_5)
        remainingFunds.assertHas(250, COIN_2)
        remainingFunds.assertHas(500, COIN_1)
    }

    @Test
    fun `withdraw funds of 1725 Euros and then 1825 Euros, from limited funds of ATM`() {
        val atm = AtmMachine(limitedFunds = true)
        val initialBalance = atm.remainingBalance

        atm.withdraw(1725)
        val withdrawal = atm.withdraw(1825)

        assertThat(withdrawal.sumOf { it.nomination }).isEqualTo(1825)

        val expectedWithdrawal = mutableListOf<Note>()
        repeat(4) { expectedWithdrawal.add(BILL_100) }
        repeat(12) { expectedWithdrawal.add(BILL_50) }
        repeat(19) { expectedWithdrawal.add(BILL_20) }
        repeat(44) { expectedWithdrawal.add(BILL_10) }
        repeat(1) { expectedWithdrawal.add(BILL_5) }
        assertThat(withdrawal)
            .containsExactlyInAnyOrderElementsOf(expectedWithdrawal)

        val expectedRemainingBalance = initialBalance - 1725 - 1825
        assertThat(atm.remainingBalance)
            .isEqualTo(expectedRemainingBalance)

        val remainingFunds = atm.remainingFunds
        remainingFunds.assertHas(0, BILL_500)
        remainingFunds.assertHas(0, BILL_200)
        remainingFunds.assertHas(0, BILL_100)
        remainingFunds.assertHas(0, BILL_50)
        remainingFunds.assertHas(0, BILL_20)
        remainingFunds.assertHas(6, BILL_10)
        remainingFunds.assertHas(98, BILL_5)
        remainingFunds.assertHas(250, COIN_2)
        remainingFunds.assertHas(500, COIN_1)
    }


    private fun List<Note>.assertHas(countOf: Int, note: Note) = assertThat(count { it == note }).isEqualTo(countOf)
}
