package tools

import java.io.File

/**
 * Helper class to make operation on [String]
 */
class StringHandler {

    companion object {

        /**
         * Read a file and return it's content as a [String]
         *
         * @param filename [String] that contains the path to the file to be read.
         * @return [String] content of the file.
         */
        fun readFile(filename: String): String {
            val inputReader = File(filename).inputStream()
            return inputReader.bufferedReader().use { it.readText() }
        }

        /**
         * Helper function to parse a [List] of [String] and transform it into [Int]
         *
         * @param line [List] of [String] to be transformed.
         * @return [List] of [Int]
         * @throws IllegalArgumentException if [line] contains non-Int characters]
         */
        fun toInts(line: List<String>): List<Int> {
            val numbers = mutableListOf<Int>()
            for (element in line) {
                if (element.isNotBlank()) {
                    numbers.add(element.toInt())
                }
            }
            return numbers
        }
    }
}