package ee.rsx.kata.codurance.atm

import ee.rsx.kata.codurance.atm.money.Cash
import ee.rsx.kata.codurance.atm.money.CashType.BILL
import ee.rsx.kata.codurance.atm.money.CashType.COIN
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
                Cash(200, BILL),
                Cash(200, BILL),
                Cash(20, BILL),
                Cash(10, BILL),
                Cash(2, COIN),
                Cash(2, COIN)
            )
    }

    @Test
    fun `withdraw funds of 1397 Euros`() {
        val atm = AtmMachine()

        val withdrawal = atm.withdraw(1397)

        assertThat(withdrawal.sumOf { it.nomination }).isEqualTo(1397)
        assertThat(withdrawal)
            .containsExactlyInAnyOrder(
                Cash(500, BILL),
                Cash(500, BILL),
                Cash(200, BILL),
                Cash(100, BILL),
                Cash(50, BILL),
                Cash(20, BILL),
                Cash(20, BILL),
                Cash(5, BILL),
                Cash(2, COIN),
            )
    }
}
