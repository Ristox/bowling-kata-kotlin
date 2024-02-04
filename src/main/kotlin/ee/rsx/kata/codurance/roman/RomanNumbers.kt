package ee.rsx.kata.codurance.roman

class RomanNumber(private val value: Int) {

    private val baseValues: List<String> = listOf(
        "",
        "I",
        "II",
        "III",
        "IV",
        "V",
        "VI",
        "VII",
        "VIII",
        "IX",
        "X"
    )

    fun stringValue(): String {
        return if (value > 0) baseValues[value] else throw IllegalArgumentException("Value must be positive integer")
    }
}

fun toRoman(value: Int): String {
    return RomanNumber(value).stringValue()
}
