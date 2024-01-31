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

    fun withdraw(amount: Int): List<Note> { //TODO refactor to single withdraw method
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

                    val numberOfNotes = sumToReduce / note.nomination
                    List(numberOfNotes) { note }
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

                if (remainingAmount == 0 || modulus == remainingAmount || funds.noneAvailableOf(note))
                    emptyList()
                else {
                    val sumOfNotes = remainingAmount - modulus
                    val requiredNotesCount = sumOfNotes / note.nomination

                    val availableNotes = funds.countOf(note)
                    val numberOfNotes = min(availableNotes, requiredNotesCount)
                    val sumToReduce = numberOfNotes * note.nomination

                    remainingAmount -= sumToReduce

                    funds.take(numberOfNotes, note)
                }
            }

        if (remainingAmount != 0) {
            funds.addAll(notesTaken)
            throw IllegalStateException("Not enough funds to withdraw required amount ($amount) - please use another ATM")
        }

        return notesTaken
    }

    private fun List<Note>.countOf(note: Note) = count { it == note }

    private fun List<Note>.noneAvailableOf(note: Note) = none { it == note }

    private fun MutableList<Note>.take(number: Int, ofNote: Note): List<Note> {
        val notesTaken: MutableList<Note> = mutableListOf()
        repeat(number) {
            val removeIndex = indexOfFirst { availableNote -> availableNote == ofNote }
            notesTaken.add(removeAt(removeIndex))
        }
        return notesTaken
    }
}
