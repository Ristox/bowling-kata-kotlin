package ee.rsx.kata.misc.person

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class PersonTest {

    val michael = Person("Michael", "Jackson", Ssn("EE", "3802923111"), LocalDate.of(2005, 7, 31))
    val john = Person("John", "Doe", Ssn("EE", ""), LocalDate.of(1985, 1, 1))
    val george = Person("George", "Bush", Ssn("EE", ""), LocalDate.of(1946, 7, 6))
    val barack = Person("Barack", "Obama", Ssn("EE", ""), LocalDate.of(1961, 8, 4))
    val james = Person("James", "Jameson", Ssn("EE", ""), LocalDate.of(2005, 8, 20))

    @Test
    fun johnIsAdult() {
        assertThat(john.isAdult()).isTrue
    }

    @Test
    fun averageAgeOfAllPersons() {
        val persons = listOf(michael, john, george, barack, james)
        val averageAge = persons.map { it.age }.average()
        assertThat(averageAge).isEqualTo(37.4)
    }

    @Test
    fun isMinor() {

        assertThat(michael.isAdult()).isFalse
    }

    @Test
    fun johnsAgeIs38() {
        assertThat(john.age).isEqualTo(38)
    }

    @Test
    fun jamesIs17() {
        assertThat(james.age).isEqualTo(17)
    }

    @Test
    fun jamesIsUnderage() {
        assertThat(james.isAdult()).isFalse
    }

    @Test
    fun personIsAdultWhenBirthDayWasYesterday() {
        val person = Person("John", "Doe", Ssn("EE", ""), LocalDate.now().minusYears(18).minusDays(1))
        assertThat(person.isAdult()).isTrue
    }

    @Test
    fun personIsSeventeenWhenBirthDayIsTomorrow() {
        val person = Person("John", "Doe", Ssn("EE", ""), LocalDate.now().minusYears(18).plusDays(1))
        assertThat(person.age).isEqualTo(17)
    }

    @Test
    fun `james cannot buy booze`() {
        assertThat(james.canBuyBooze()).isFalse
    }

    @Test
    fun `person over the age of twenty one can buy booze`() {
        val person = Person("John", "Doe", Ssn("EE", ""), LocalDate.now().minusYears(21).minusDays(1))
        assertThat(person.canBuyBooze()).isTrue
    }

    @Test
    fun `person under the age of twenty one cannot buy booze`() {
        val person = Person("John", "Doe", Ssn("EE", ""), LocalDate.now().minusYears(21).plusDays(1))
        assertThat(person.canBuyBooze()).isFalse
    }

}
