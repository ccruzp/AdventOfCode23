package day1

import tools.StringHandler

/**
 * Class that calculates the calibration values of the sled.
 */
class Calibrator {
    companion object {
        /**
         * Calculate the calibration value given a list of entries.
         *
         * @param values list of entries used to calculate the calibration.
         * @return calibration number.
         */
        fun calibrate(values: List<String>): Int {
            return values.fold(0) { total, line ->
                total + extractCalibrationValues(line)
            }
        }

        /**
         * Extract the calibration values from a line of text.
         *
         * @param line line of text to extract values from.
         */
        private fun extractCalibrationValues(line: String): Int {
            val tokens = Tokenizer.tokenize(line)

            val firstValue = if (tokens.isNotEmpty()) tokens.first() else CalibrationElement.ZERO
            val lastValue = if (tokens.isNotEmpty()) tokens.last() else CalibrationElement.ZERO

            return ("" + firstValue.numValue + lastValue.numValue).toInt()
        }
    }
}

fun main() {
    val content = StringHandler.readFile("src\\main\\resources\\calibration.txt")
    val splitByLines = content.lines()
    println(Calibrator.calibrate(splitByLines))
}