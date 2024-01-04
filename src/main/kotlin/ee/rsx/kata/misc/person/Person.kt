package ee.rsx.kata.misc.person

import java.time.LocalDate

data class Person(
    val firstName: String,
    val lastName: String,
    val socialSecurityNumber: Ssn,
    val birthDay: LocalDate
) {
    val age: Int
        get() = if (birthDay.dayOfYear > LocalDate.now().dayOfYear) {
            LocalDate.now().year - birthDay.year - 1
        } else {
            LocalDate.now().year - birthDay.year
        }

    fun isAdult() = age >= 18

    fun initials() = "$firstName[0]. $lastName[0]."

    fun fullName() = "$firstName $lastName"

    fun canBuyBooze() = age >= 21
}

data class Ssn(
    val countryCode: String,
    val value: String
)

data class Contact(
    val type: ContactType,
    val value: String
)

enum class ContactType {
    EMAIL,
    PHONE,
    ADDRESS
}

data class Customer(
    val person: Person,
    val contacts: List<Contact> = emptyList()
) {
    fun isAdult() = person.isAdult()
}
