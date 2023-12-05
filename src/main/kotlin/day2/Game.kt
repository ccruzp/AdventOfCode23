package day2

import tools.StringHandler

/**
 * Class that represents a die game played and contains the relevant information.
 *
 * @property id identifier of the created game.
 */
class Game(val id: Int) {

    /**
     * Contains all the [Round]s played this game.
     */
    private val rounds = mutableListOf<Round>()

    companion object {
        /**
         * Create the Game described by the provided [String]
         *
         * @param line [String] that describes the Game.
         * @return Game described by [line]
         */
        fun fromString(line: String): Game {
            val split = line.split(":")
            val gameId = split[0].split(" ")[1]
            val rest = split[1].split(";")

            val game = Game(id = gameId.toInt())
            rest.forEach {
                game.addRounds(it)
            }

            return game
        }
    }

    /**
     * Verifies if this game is possible given the provided [red], [green] and [blue]
     *
     * @param red [Int] value of the red cubes.
     * @param green [Int] value of the green cubes.
     * @param blue [Int] value of the blue cubes.
     * @return _true_ if the [Game] is possible; otherwise returns _false_.
     */
    internal fun isPossible(red: Int, green: Int, blue: Int): Boolean {
        return getMax("red") <= red
                && getMax("green") <= green
                && getMax("blue") <= blue
    }

    /**
     * Calculate the power of a set of cubes
     */
    internal fun getMinPower() = getMax("red") * getMax("green") * getMax("blue")

    /**
     * Get the max number of cubes of the specified [color] used in this [Game].
     *
     * @param color color to obtain the max of.
     * @return Max number of cubes of the specified [color] used in this [Game]
     */
    private fun getMax(color: String): Int {
        var maxRed = 0
        var maxGreen = 0
        var maxBlue = 0

        rounds.forEach {
            if (maxRed < it.red) {
                maxRed = it.red
            }

            if (maxGreen < it.green) {
                maxGreen = it.green
            }

            if (maxBlue < it.blue) {
                maxBlue = it.blue
            }
        }

        return when (color.lowercase()){
            "red" -> maxRed
            "green" -> maxGreen
            "blue" -> maxBlue
            else -> 0
        }
    }

    /**
     * Add all the rounds described in the provided [String]
     *
     * @param content [String] that describes a set of rounds.
     */
    fun addRounds(content: String) {
        val rounds = content.split(";")
        for (round in rounds) {
            val colors = round.split(",")

            var red: String? = null
            var green: String? = null
            var blue: String? = null

            for (color in colors) {
                when {
                    color.contains("red") -> red = color.split(" ")[1]
                    color.contains("green") -> green = color.split(" ")[1]
                    color.contains("blue") -> blue = color.split(" ")[1]
                    else -> print("Color \"${color.split(" ")[1]}\" was not recognized. It was omitted")
                }
            }

            addRound(
                red = if (!red.isNullOrBlank() && red.all { char -> char.isDigit() }) red.toInt() else 0,
                green = if (!green.isNullOrBlank() && green.all { char -> char.isDigit() }) green.toInt() else 0,
                blue = if (!blue.isNullOrBlank() && blue.all { char -> char.isDigit() }) blue.toInt() else 0

            )
        }
    }

    /**
     * Add a [Round] to this game.
     *
     * @param red number of red balls.
     * @param green number of green balls.
     * @param blue number of blue balls.
     */
    private fun addRound(red: Int, green: Int, blue: Int) {
        rounds.add(Round(red, green, blue))
    }

    override fun toString(): String {
        var string = "Game $id\n"
        for (round in rounds) {
            string += round
            string += '\n'
        }
        return string
    }
}

fun main() {
    val content = StringHandler.readFile("src\\main\\resources\\diceGameResults.txt")
    val games = mutableListOf<Game>()

    content.lines().map {
        if (it.isNotBlank()) {
            games.add(Game.fromString(it))
        }
    }

    val maxRed = 12
    val maxGreen = 13
    val maxBlue = 14

    var idSum = 0
    games.forEach {
        if (it.isPossible(maxRed, maxGreen, maxBlue)) {
            idSum += it.id
        }
    }

    var sum = 0
    games.forEach { sum += it.getMinPower() }
    println("Sum of powers: $sum")
}