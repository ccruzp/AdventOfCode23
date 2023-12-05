package tools

import java.io.File

class StringHandler {

    companion object {
        fun splitByLines(content: String): List<String> {
            return content.lines()
        }

        fun readFile(filename: String): String {
            val inputReader = File(filename).inputStream()
            return inputReader.bufferedReader().use { it.readText() }
        }
    }
}