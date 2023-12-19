package day6

import tools.StringHandler

/**
 * Class that represents a boat race.
 *
 * @property duration Duration of this Race.
 * @property record Record distance achieved in this Race.
 */
class Race (val duration: Long, val record: Long) {

    /**
     * Get the distance covered in this Race by pressing the boat's button for [chargingTime] milliseconds.
     *
     * @param chargingTime [Long] amount of milliseconds to press the boat's button.
     * @return [Long] that represents the distance covered by the boat when the time is over.
     * @throws IllegalArgumentException if the given [chargingTime] is negative.
     */
    fun getDistance(chargingTime: Long): Long {
        when {
            chargingTime < 0 -> throw IllegalArgumentException("Time cannot be negative!")
            chargingTime > duration -> return 0
            else -> {
                val racingTime = duration - chargingTime
                return racingTime * chargingTime
            }
        }
    }

    /**
     * Check if the covered [distance] wins this Race.
     *
     * @param distance Distance covered by the boat in this Race.
     * @return _true_ if the boat breaks the current record; otherwise returns _false_.
     */
    fun breaksRecord(distance: Long): Boolean {
        return record < distance
    }
}

fun main() {
    val input = StringHandler.readFile(filename = "src/main/resources/boatRaces.txt").lines()
    val times = input[0].split(':')[1].filterNot { it.isWhitespace() }
    val records = input[1].split(':')[1].filterNot { it.isWhitespace() }

    val races = mutableListOf(Race(duration = times.toLong(), record = records.toLong()))

    val winning = races.map {
        var possibleWin = 0
        for (i in 0..it.duration) {
            val distance = it.getDistance(chargingTime = i)
            possibleWin += if (it.breaksRecord(distance = distance)) { 1 } else 0
        }
        possibleWin
    }

    val totalMargin = winning.foldRight(initial = 1) { margin: Int, acc: Int -> margin * acc }
    println("Total margin -> $totalMargin")
}