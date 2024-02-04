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
        if (value <= 0)
            throw IllegalArgumentException("Value must be positive integer")

        if (value > 39)
            throw IllegalArgumentException("Unsupported yet")

        val multiplierValue = 10
        val multiplierLiteral = "X"

        val remainder = value % multiplierValue
        val timesOfMultiplier = (value - remainder) / multiplierValue

        val multiplierLiterals = List(timesOfMultiplier) { multiplierLiteral }.joinToString("")

        return multiplierLiterals + baseValues[remainder]
    }
}

fun toRoman(value: Int): String {
    return RomanNumber(value).stringValue()
}
