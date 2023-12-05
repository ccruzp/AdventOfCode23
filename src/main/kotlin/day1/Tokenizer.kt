package day1

class Tokenizer {
    companion object {
        fun tokenize(line: String): List<CalibrationElement> {
            val tokens = mutableListOf<CalibrationElement>()
            for (i in line.indices) {
                when {
                    line[i].isDigit() -> {
                        val digit = CalibrationElement.from(line[i])
                        if (digit != null) {
                            tokens.add(digit)
                        }
                    }
                    else -> {
                        val token = CalibrationElement.searchToken(line.substring(i))
                        if (token != null) {
                            tokens.add(token)
                        }
                    }
                }
            }
            return tokens
        }
    }
}