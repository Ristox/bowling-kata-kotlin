package ee.rsx.kata.codurance.atm

import ee.rsx.kata.codurance.atm.money.Note
import ee.rsx.kata.codurance.atm.money.Note.*
import kotlin.math.min

class AtmMachine(limitedFunds: Boolean = false) {

    private val funds: MutableList<Note>?

    init {
        funds = if (limitedFunds)
            filledFunds()
        else
            null
    }

    private fun filledFunds(): MutableList<Note> {
        val funds = mutableListOf<Note>()
        repeat(2) { funds.add(BILL_500) }
        repeat(3) { funds.add(BILL_200) }
        repeat(5) { funds.add(BILL_100) }
        repeat(12) { funds.add(BILL_50) }
        repeat(20) { funds.add(BILL_20) }
        repeat(50) { funds.add(BILL_10) }
        repeat(100) { funds.add(BILL_5) }
        repeat(250) { funds.add(COIN_2) }
        repeat(500) { funds.add(COIN_1) }
        funds.sortByDescending { it.nomination }
        return funds
    }

    val remainingBalance: Int
        get() = funds?.let { funds.sumOf { it.nomination } }
            ?: throw IllegalStateException("Cannot determine remaining balance, ATM works with limitless funds")

    val remainingFunds: List<Note>
        get() = funds?.let { funds.toList() }
            ?: throw IllegalStateException("Cannot determine remaining funds, ATM works with limitless funds")

    fun withdraw(amount: Int): List<Note> {
        return if (funds != null) {
            withdrawFromFunds(amount)
        } else
            withdrawLimitless(amount)
    }

    private fun withdrawLimitless(amount: Int): List<Note> {
        var remainingAmount = amount

        return Note.entries
            .sortedByDescending { it.nomination }
            .flatMap { note ->
                val modulus = remainingAmount % note.nomination

                if (modulus == remainingAmount){
                    emptyList()}
                else {
                    val sumToReduce = remainingAmount - modulus
                    remainingAmount -= sumToReduce

                    val notesCount = sumToReduce / note.nomination
                    List(notesCount) { note }
                }
            }

    }

    private fun withdrawFromFunds(amount: Int): List<Note> {
        var remainingAmount = amount

        requireNotNull(funds)

        val notesTaken = Note.entries
            .sortedByDescending { it.nomination }
            .flatMap { note ->
                val modulus = remainingAmount % note.nomination

                if (remainingAmount == 0 || modulus == remainingAmount || funds.none { it == note })
                    emptyList()
                else {
                    val sumOfNotes = remainingAmount - modulus
                    val requiredNotesCount = sumOfNotes / note.nomination

                    val availableNotes = funds.countOf(note)
                    val howManyToTake = min(availableNotes, requiredNotesCount)

                    val sumToReduce = howManyToTake * note.nomination
                    remainingAmount -= sumToReduce

                    funds.take(howManyToTake, note)
                }
            }

        // TODO if remaining amount is not 0, then we don't have enough of suitable notes - put them back and throw error

        return notesTaken
    }

    private fun List<Note>.countOf(note: Note) = count { it == note }

    private fun MutableList<Note>.take(times: Int, note: Note): List<Note> {
        val notesTaken: MutableList<Note> = mutableListOf()
        repeat(times) {
            val removeIndex = indexOfFirst { availableNote -> availableNote == note }
            notesTaken.add(removeAt(removeIndex))
        }
        return notesTaken
    }
}
