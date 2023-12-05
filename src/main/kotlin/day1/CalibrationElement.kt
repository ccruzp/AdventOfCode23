package day1

/**
 * Class that represents the elements used to calculate the calibration value of the for the snow machine.
 *
 * @property numValue [Char] that represents the numerical value of this calibration element.
 */
enum class CalibrationElement(val numValue: Char) {
    ZERO('0'),
    ONE('1'),
    TWO('2'),
    THREE('3'),
    FOUR('4'),
    FIVE('5'),
    SIX('6'),
    SEVEN('7'),
    EIGHT('8'),
    NINE('9');

    companion object {

        /**
         * Retrieve the calibration element associated to [value].
         *
         * @param value [Char] to be used.
         * @return associated calibration element if it exists, otherwise returns _null_
         */
        @JvmStatic
        fun from(value: Char): CalibrationElement? {
            return entries.firstOrNull { it.numValue == value }
        }

        /**
         * Search for a token in the provided [line].
         *
         * @param line [String]to be searched
         * @return calibration element found in [line] if any, otherwise _null_
         */
        @JvmStatic
        fun searchToken(line: String): CalibrationElement? {
            for (element in entries) {
                if (line.startsWith(element.toString())) {
                    return element
                }
            }
            return null
        }
    }
}