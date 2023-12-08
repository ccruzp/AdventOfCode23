package day4

import tools.StringHandler

/**
 * Class that represents a Scratchcard.
 */
class Scratchcard(val winningNumbers: List<Int>, val obtainedNumbers: List<Int>) {

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

            val winners = toInts(first)
            val obtained = toInts(second)

            return Scratchcard(winners, obtained)
        }

        /**
         * Helper function to parse a [List] of [String] and transform it into [Int]
         *
         * @param line [List] of [String] to be transformed.
         * @return [List] of [Int]
         * @throws IllegalArgumentException if [line] contains non-Int characters]
         */
        private fun toInts(line: List<String>): List<Int> {
            val numbers = mutableListOf<Int>()
            for (element in line) {
                if (element.isNotBlank()) {
                    numbers.add(element.toInt())
                }
            }
            return numbers
        }
    }

    /**
     * @return [Int] that contains the amount of points given by this Scratchcard
     */
    fun getPoints(): Int {
        var numOfMatch = 0
        for (number in obtainedNumbers) {
            if (winningNumbers.contains(number)) {
                numOfMatch += 1
            }
        }

        return when (numOfMatch) {
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

    override fun toString(): String {
        val winning = winningNumbers.fold("") { acc: String, i: Int -> "$acc $i" }
        val obtained = obtainedNumbers.fold("") { acc: String, i: Int -> "$acc $i" }

        return "$winning | $obtained"

    }
}

fun main() {
    //val data = StringHandler.readFile("src\\main\\resources\\fakeset.txt")
    val data = StringHandler.readFile("src\\main\\resources\\scratchcards.txt")
    val cards = mutableListOf<Scratchcard>()
    for (line in data.lines()) {
        if (line.isNotBlank()) {
            cards.add(Scratchcard.from(line))
        }
    }

    val totalPoints = cards.fold(initial = 0) { acc: Int, scratchcard: Scratchcard -> acc + scratchcard.getPoints() }
    println("Total points -> $totalPoints")
}