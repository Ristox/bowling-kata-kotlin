package kata.bowling

class Bowling {
    val rolls: MutableList<Int> = mutableListOf()

    fun roll(pins: Int) {
        validateRoll(pins)
        rolls.add(pins)
    }

    fun score(): Int {
        val frames: MutableList<Frame> = mutableListOf()
        var i = 0
        while (i < rolls.size) {
            if (rolls[i] == 10) {
                frames.add(Frame(rolls[i], 0))
            } else {
                frames.add(Frame(rolls[i], rolls[i+1]))
                i++
            }
            i++
        }

        val baseScore = frames.sumOf { it.baseScore() }
        var bonus = 0

        frames.forEachIndexed { index, frame ->
            if (index < frames.size - 1) {
                if (frame.isSpare()) {
                    bonus += frames[index+1].roll1
                }
                if (frame.isStrike()) {
                    bonus += frames[index+1].roll1
                    bonus += frames[index+1].roll2
                }
            }
        }

        return baseScore + bonus
    }

    private fun validateRoll(pins: Int) {
        if (pins > 10 || pins < 0)
            throw IllegalArgumentException()
    }
}

data class Frame(
    val roll1: Int,
    val roll2: Int
) {
    fun isSpare() = roll1 + roll2 == 10 && roll2 != 0
    fun isStrike() = roll1 == 10 && roll2 == 0
    fun baseScore() = roll1 + roll2
}
