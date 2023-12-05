package tools

/**
 * Class that represents a Matrix.
 */
class Matrix() {

    // Inner representation of the Matrix
    private val rows = mutableListOf(mutableListOf<Char>())

    private operator fun set(i: Int, value: MutableList<Char>) {
        if (i >= rows.size) {
            return
        }
        rows[i] = value
    }

    private operator fun get(i: Int): MutableList<Char> {
        if (i >= rows[i].size) {
            return emptyList<Char>().toMutableList()
        }
        return rows[i]
    }

    companion object  {
        /**
         * Create a matrix from a text file. If the Matrix cannot be created, throws an exception.
         *
         * @param data [String] that contains the path to the file to be used as input.
         * @return Matrix associated to the data provided.
         */
        fun from(data: String): Matrix {
            val matrix = Matrix()
            val lines = data.lines()
            for (i in lines.indices) {
                matrix[i] = mutableListOf()
                for (j in 0..< lines[i].length) {
                    matrix[i].add(j, lines[i][j])
                }
            }

            /*for (line in data.lines()) {
                val newCol = mutableListOf<Char>()
                for (char in line) {
                    newCol.add(char)
                }
                matrix.rows.add(newCol)
            }*/
            return matrix
        }
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

        println("Rows: ${rows.size}\nColumns: ${rows[0].size}\n")
        for (i in -1..1) {
            for (j in -1..1) {
                if (i == 0 && j == 0) {
                    continue
                }
                val adjacentRow = row + i
                val adjacentColumn = column + j

                if (adjacentRow >= 0
                    && adjacentColumn >= 0
                    && adjacentRow < rows.size
                    && adjacentColumn < rows[0].size
                    ) {
                    coordinates.add(Coordinate(adjacentRow, adjacentColumn))
                }
            }
        }
        return coordinates
    }

    override fun toString(): String {
        var string = ""
        for (row in rows) {
            for (column in row) {
                string += column
            }
            string += '\n'
        }
        return string
    }
}