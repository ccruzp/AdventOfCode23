package day3

import tools.Matrix
import tools.StringHandler

class Schematic(data: String) {

    /**
     * [Matrix] used to represent this Schematic.
     */
    val matrix = Matrix.from(data)


}

fun main() {
    val data = StringHandler.readFile("src\\main\\resources\\schematic.txt")

    val schematic = Schematic(data)
    println("Schematic:\n$schematic")

    val i = 4
    val j = 10
    println("Adjacent Position ($i, $j): ${schematic.matrix.getAdjacent(row = i, column = j)}")
}