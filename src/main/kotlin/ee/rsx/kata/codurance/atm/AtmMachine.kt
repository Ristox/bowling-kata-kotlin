package ee.rsx.kata.codurance.atm

import ee.rsx.kata.codurance.atm.money.Note

class AtmMachine {

    fun withdraw(amount: Int): List<Note> {
        var remainingAmount = amount

        return Note.entries
            .sortedByDescending { it.nomination }
            .flatMap { note ->
                val modulus = remainingAmount % note.nomination

                if (modulus == remainingAmount)
                    emptyList()
                else {
                    val sumToReduce = remainingAmount - modulus
                    remainingAmount -= sumToReduce

                    val notesCount = sumToReduce / note.nomination
                    MutableList(size = notesCount) { note }
                }
            }
    }
}
