package tools

/**
 * Class that represents a Matrix.
 *
 * @property rows number of rows in this matrix
 * @property columns number of columns in this matrix.
 */
class Matrix(private val rows: Int, private val columns: Int) {

    // Inner representation of the matrix.
    private var innerMatrix = Array(rows) { Array(columns) {' '} }

    companion object {
        /**
         * @param data [String] to be parsed into a Matrix.
         * @return Matrix associated to the provided [String]
         */
        fun from(data: String): Matrix {
            val maxRows = data.lines().size
            val maxCol = data.lines()[0].length

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

    private operator fun get(i: Int): Array<Char> {
        return innerMatrix[i]
    }

    private operator fun set(value: Char, at: Coordinate) {
        add(value = value, at = at)
    }

    /**
     * Adds an element to this Matrix.
     *
     * @param at Position in this Matrix where the element is to be inserted.
     * @param value value to be added to this Matrix.
     * @return _true_ if [value] was successfully added at the provided [Coordinate]; otherwise returns _false_.
     */
    private fun add(value: Char, at: Coordinate): Boolean {
        if (at.row >= rows || at.column >= columns) {
            return false
        }

        innerMatrix[at.row][at.column] = value
        return true
    }

    /**
     * Get the value at the specified [Coordinate]
     *
     * @param from [Coordinate] to get the value from.
     * @return value stored in the provided [Coordinate]
     */
    fun get(from: Coordinate): Char {
        if (from.row >= rows || from.column >= columns) {
            throw IllegalArgumentException("Coordinates $from are outside this Matrix")
        }

        return innerMatrix[from.row][from.column]
    }

    /**
     * Given the position ([row], [column]) of an element, it returns the coordinates of all the surrounding positions.
     *
     * @param row row where the element is.
     * @param column column where the element is.
     * @return [List] that contains the coordinates (row, column) of all the adjacent positions.
     */
    fun getAdjacent(row: Int, column: Int): List<Coordinate> {
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

    override fun toString(): String {
        var string = ""
        for (row in innerMatrix.indices) {
            for (col in innerMatrix[row].indices) {
                string += "${innerMatrix[row][col]}"
            }
            string += '\n'
        }
        return string
    }
}