package day3

import tools.Coordinate

/**
 * Class that represents a number in the Schematics of the engine of the snow machine.
 */
class Number() {
    // Inner representation of the number.
    private val digits = mutableListOf<Coordinate>()

    /**
     * Add a [digit] to this number.
     *
     * @param digit Digit to be concatenated of this number.
     */
    fun concatenate(digit: Coordinate) {
        digits.add(digit)
    }

    /**
     * @return [List] of [Coordinate] of each digit in this Number.
     */
    fun toList(): List<Coordinate> {
        return digits.toList()
    }

    override fun toString(): String {
        var string = "["
        for (digit in digits) {
            string += digit
        }
        string += "]"
        return string
    }
}
