package tools

/**
 * Class that represents a Matrix.
 *
 * @property numRows number of rows in this matrix
 * @property numColumns number of columns in this matrix.
 */
class Matrix(private val numRows: Int, private val numColumns: Int) {

    // Inner representation of the matrix.
    var innerMatrix: Array<Array<Char?>> = Array(numRows) { arrayOfNulls(numColumns) }

    companion object {
        /**
         * @param data [String] to be parsed into a Matrix.
         * @return Matrix associated to the provided [String]
         */
        fun from(data: String): Matrix {
            val maxRows = data.lines().size
            val maxCol = data.lines()[0].length

            println("Creating $maxRows x $maxCol matrix")
            val matrix = Matrix(maxRows, maxCol)

            for (i in 0..< maxRows) {
                if (data.lines()[i].isBlank()) {
                    continue
                }
                for (j in 0..< maxCol) {
                    matrix[i][j] = data.lines()[i][j]
                }
            }
            return matrix
        }
    }

    /**
     * Get the value at the specified [Coordinate]
     *
     * @param from [Coordinate] to get the value from.
     * @return value stored in the provided [Coordinate]
     */
    fun get(from: Coordinate): Char? {
        if (from.row >= numRows || from.column >= numColumns) {
            throw IllegalArgumentException("Coordinates $from are outside this Matrix")
        }

        return innerMatrix[from.row][from.column]
    }

    /**
     * Operator to obtain all the values in the provided [row]
     *
     * @param row row of this Matrix to obtain the values from.
     * @return an [Array] that contains all the values in this Matrix at [row].
     */
    operator fun get(row: Int): Array<Char?> {
        return innerMatrix[row]
    }

    /**
     * Operator to set a [value] [at] a given [Coordinate]
     *
     * @param value to be set at the given [Coordinate].
     * @param at [Coordinate] to set [value] to.
     */
    operator fun set(value: Char, at: Coordinate) {
        add(value = value, at = at)
    }

    /**
     * Return the [Coordinate] of the next position in this row.
     *
     * If the given [position] is the last element in the row, returns _null_
     *
     * @param position [Coordinate] of the position to obtain the next from.
     * @return [Coordinate] of the next element in the row (if it exists); otherwise returns _null_
     */
    fun getNextInRow(position: Coordinate): Coordinate? {
        val nextCol = position.column + 1
        return if (nextCol >= numColumns) null else Coordinate(position.row, nextCol)
    }

    /**
     * Check if [coordinate] is adjacent to any [Coordinate] the given [List]
     *
     * @param coordinate [Coordinate] to check
     * @param list [List] of [Coordinate]
     * @return _true_ if [coordinate] is adjacent to any [Coordinate] in [list]
     */
    fun isAdjacent(coordinate: Coordinate, list: List<Coordinate>): Boolean {
        val adjacents = getAdjacent(coordinate)
        for (adjacent in adjacents) {
            if (list.contains(adjacent)) {
                return true
            }
        }
        return false
    }

    /**
     * Returns the [Coordinate] adjacent to the given [position].
     *
     * @param position [Coordinate] in this Matrix.
     * @return [List] of adjacent [Coordinate] to the given [position]
     */
    fun getAdjacent(position: Coordinate): List<Coordinate> {
        return getAdjacent(position.row, position.column)
    }

    /**
     * Given the position ([row], [column]) of an element, it returns the coordinates of all the surrounding positions.
     *
     * @param row row where the element is.
     * @param column column where the element is.
     * @return [List] that contains the coordinates (row, column) of all the adjacent positions.
     */
    private fun getAdjacent(row: Int, column: Int): List<Coordinate> {
        val coordinates = mutableListOf<Coordinate>()

        for (i in -1..1) {
            for (j in -1..1) {
                if (i == 0 && j == 0) {
                    continue
                }
                val adjacentRow = row + i
                val adjacentColumn = column + j

                if (adjacentRow >= 0
                    && adjacentColumn >= 0
                    && adjacentRow < innerMatrix.size
                    && adjacentColumn < innerMatrix[row].size
                    ) {
                    coordinates.add(Coordinate(adjacentRow, adjacentColumn))
                }
            }
        }
        return coordinates
    }

    /**
     * Adds an element to this Matrix.
     *
     * @param at Position in this Matrix where the element is to be inserted.
     * @param value value to be added to this Matrix.
     * @return _true_ if [value] was successfully added at the provided [Coordinate]; otherwise returns _false_.
     */
    private fun add(value: Char, at: Coordinate): Boolean {
        if (at.row >= numRows || at.column >= numColumns) {
            return false
        }

        innerMatrix[at.row][at.column] = value
        return true
    }

    override fun toString(): String {
        var string = ""
        for (row in innerMatrix.indices) {
            for (col in innerMatrix[row].indices) {
                val value = innerMatrix[row][col]
                if (value != null) {
                    string += "$value"
                }
            }
            string += '\n'
        }
        return string
    }
}