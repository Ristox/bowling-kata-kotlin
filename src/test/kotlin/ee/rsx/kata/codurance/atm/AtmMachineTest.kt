package ee.rsx.kata.codurance.atm

import ee.rsx.kata.codurance.atm.money.Cash
import ee.rsx.kata.codurance.atm.money.CashType.BILL
import ee.rsx.kata.codurance.atm.money.CashType.COIN
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.Test

class AtmMachineTest {

    @Test
    fun test() {
        val atm = AtmMachine()

        assertThat(atm).isNotNull
    }

    @Test
    fun `withdraw funds of 434 Euros`() {
        val atm = AtmMachine()

        val withdrawal = atm.withdraw(434)

        assertThat(withdrawal.sumOf { it.nomination }).isEqualTo(434)
        assertThat(withdrawal)
            .containsExactlyInAnyOrder(
                Cash(200, BILL),
                Cash(200, BILL),
                Cash(20, BILL),
                Cash(10, BILL),
                Cash(2, COIN),
                Cash(2, COIN)
            )
    }
}
