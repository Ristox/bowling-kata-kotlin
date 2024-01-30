package ee.rsx.kata.codurance.atm

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
}
