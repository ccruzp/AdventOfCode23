package day4

import tools.StringHandler

/**
 * Class that represents a Scratchcard.
 */
class Scratchcard(private val winningNumbers: List<Int>, private val obtainedNumbers: List<Int>) {

    companion object {
        /**
         * Parse a [String] and return the associated [Scratchcard]
         *
         * @param data [String] to be parsed.
         * @return [Scratchcard] associated to the provided [data]
         * @throws IllegalArgumentException if the provided [data] cannot be parsed into a [Scratchcard]
         */
        fun from(data: String): Scratchcard {
            val numbers = data.split(':')[1]
            val first = numbers.split('|')[0].split(' ')
            val second = numbers.split('|')[1].split(' ')

            val winners = StringHandler.toInts(line = first)
            val obtained = StringHandler.toInts(line = second)

            return Scratchcard(winningNumbers = winners, obtainedNumbers = obtained)
        }
    }

    /**
     * @return [Int] that contains the amount of points given by this Scratchcard
     */
    fun getPoints(): Int {
        return when (val numOfMatch = getNumberOfMatches()) {
            0 -> 0
            1 -> 1
            else -> {
                var points = 1
                for (i in 1..< numOfMatch) {
                    points *= 2
                }
                points
            }
        }
    }

    /**
     * @return the number of matches between the [obtainedNumbers] and the [winningNumbers]
     */
    fun getNumberOfMatches(): Int {
        var numOfMatch = 0
        for (number in obtainedNumbers) {
            if (winningNumbers.contains(number)) {
                numOfMatch += 1
            }
        }
        return numOfMatch
    }

    override fun toString(): String {
        val winning = winningNumbers.fold(initial = "") { acc: String, i: Int -> "$acc $i" }
        val obtained = obtainedNumbers.fold(initial = "") { acc: String, i: Int -> "$acc $i" }

        return "$winning | $obtained"

    }
}

fun main() {
    val data = StringHandler.readFile("src\\main\\resources\\scratchcards.txt")
    val cards = mutableListOf<Scratchcard>()
    val lines = data.lines()
    val totalCards = MutableList(lines.size -1) { 1 }
    for (i in lines.indices) {
        if (lines[i].isNotBlank()) {
            val card = Scratchcard.from(lines[i])
            cards.add(card)

            val numberOfMatches = card.getNumberOfMatches()
            if (numberOfMatches == 0) {
                continue
            }

            for (j in 1..numberOfMatches) {
                totalCards[i + j] += totalCards[i]
            }
        }
    }

    val totalPoints = cards.fold(initial = 0) { acc: Int, scratchcard: Scratchcard -> acc + scratchcard.getPoints() }
    println("Total points -> $totalPoints")

    val sum = totalCards.fold(initial = 0) { acc: Int, i: Int -> acc + i }
    println("Total cards -> $sum")
}