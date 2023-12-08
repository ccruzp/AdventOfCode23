package day3

import tools.Coordinate
import tools.Matrix
import tools.StringHandler

import kotlin.collections.ArrayDeque

/**
 * Class the represents the Schematics of the snow machine.
 *
 * @param data [String] representation of this Schematic.
 */
class Schematic(data: String) {

    /**
     * [Matrix] used to represent this Schematic.
     */
    internal val matrix = Matrix.from(data)

    /**
     * Retrieve all the part numbers in this Schematic.
     *
     * A part number is defined as any number that is adjacent (even diagonally) to a symbol (different that'.').
     *
     * @return [List] of [Number] of parts in this Schematic.
     */
    fun calculatePartNumbers(): List<Number> {
        val parts = mutableListOf<Number>()
        val numbers = getNumbersPosition()

        for (number in numbers) {
            val adjacentPositions = mutableSetOf<Coordinate>()
            for (digit in number.toList()) {
                adjacentPositions.addAll(getAdjacentCoordinates(digit))
            }
            if (containsSymbol(adjacentPositions.toList())) {
                parts.add(number)
            }
        }
        return parts
    }

    /**
     * Verify is the list of given coordinates contains a symbol.
     *
     * @param coordinates [Coordinate] to check.
     * @return _true_ if there is, at least, one symbol; otherwise returns _false_
     */
    private fun containsSymbol(coordinates: List<Coordinate>): Boolean {
        for (coordinate in coordinates) {
            val value = matrix.get(coordinate)?: return false
            val isAlphaNumeric = value.isLetterOrDigit()

            if (!isAlphaNumeric && value != '.') {
                return true
            }
        }
        return false
    }

    /**
     * Given the [position] of a digit from a number, calculate adjacent [Coordinate] of the number the digit belongs
     * to.
     *
     * @param position [Coordinate] of the digit.
     * @return The [List] of [Coordinate] adjacent to the number at [position]
     */
    private fun getAdjacentCoordinates(position: Coordinate): Set<Coordinate> {
        val numberPositions = ArrayDeque<Coordinate>()
        val adjacentCoordinates = mutableSetOf<Coordinate>()
        numberPositions.add(position)
        while (numberPositions.isNotEmpty()) {
            val num = numberPositions.removeFirst()
            val nextInRow = matrix.getNextInRow(num)
            if (nextInRow != null
                && matrix.get(nextInRow) != null
                && matrix.get(nextInRow)!!.isDigit()) {
                    numberPositions.add(nextInRow)
            }

            adjacentCoordinates.addAll(matrix.getAdjacent(num))
        }
        return adjacentCoordinates
    }

    /**
     * Calculates the [Coordinate] of each [Number] in this Schematic.
     *
     * @return [List] of [Number] composed by the digits found in this Schematic.
     */
    private fun getNumbersPosition(): List<Number> {
        val digits = getDigitsPosition().toMutableList()
        val numbers = mutableListOf<Number>()

        var number = Number()
        while (digits.isNotEmpty()) {
            val current = digits.removeFirst()
            number.concatenate(current)
            if (digits.isNotEmpty()) {
                if (current.row == digits.first().row
                    && (current.column + 1) == digits.first().column
                ) {
                    continue
                } else {
                    numbers.add(number)
                    number = Number()
                }
            } else {
                numbers.add(number)
            }
        }
        return numbers.toList()
    }

    /**
     * @return [List] that contains the [Coordinate] of all the digits in this Schematic.
     */
    private fun getDigitsPosition(): List<Coordinate> {
        val coordinates = mutableListOf<Coordinate>()
        for (i in matrix.innerMatrix.indices) {
            for (j in matrix.innerMatrix[i].indices) {
                val value = matrix[i][j]
                if (value != null && value.isDigit()) {
                    coordinates.add(Coordinate(i,j))
                }
            }
        }
        return coordinates
    }

    override fun toString(): String {
        return matrix.toString()
    }
}

fun main() {
    val data = StringHandler.readFile("src\\main\\resources\\schematic.txt")

    val schematic = Schematic(data)
    val partNumbers = schematic.calculatePartNumbers()
    var sum = 0
    for (number in partNumbers) {
        var string = ""
        for (digit in number.toList()) {
            string += schematic.matrix.get(digit)
        }
        sum += string.toInt()
    }
    println("Total sum of parts numbers: $sum")
}