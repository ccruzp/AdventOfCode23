package day5

/**
 * Class used to represents the different maps in an almanac
 */
class AlmanacMap {

    private val charts = mutableListOf<Chart>()

    /**
     * Inner model of how the AlmanacMap is modeled.
     *
     * @property src [Int] starting value of this Chart.
     * @property dst [Int] ending value of this Chart.
     * @property length [Int] that represents the amount of values in this Chart.
     */
    private data class Chart(val src: Long, val dst: Long, val length: Long)

    companion object {

        /**
         * Create an Almanac from the given [List] of [String]
         *
         * @param input [List] that contains the values to fill this Map with.
         * @return Map filled with the provided values.
         */
        fun from(input: List<String>): AlmanacMap {
            val map = AlmanacMap()
            for (line in input) {
                if (line.isEmpty()) {
                    continue
                }
                val values = line.split(' ')
                map.addChart(src = values[1], dst = values[0], length = values[2])
            }
            return map
        }
    }

    /**
     * Add a new chart to this Map.
     *
     * @param src [String] starting value of the source this chart.
     * @param dst [String] starting value of the destination of this Chart.
     * @param length [String] length of this chart.
     */
    fun addChart(src: String, dst: String, length: String) {
        charts.add(Chart(src = src.toLong(), dst = dst.toLong(), length = length.toLong()))
    }

    /**
     * Get the associated destination value to the provided source value.
     *
     * @param src [Long] to obtain the associated value from.
     * @return destination value associated to the provided source.
     */
    fun getValue(src: Long): Long {
        for (chart in charts) {
            if (chart.src <= src && src < (chart.src + chart.length)) {
                val offset = src - chart.src
                return chart.dst + offset
            }
        }
        return src
    }

    override fun toString(): String {
        var string = ""
        for (chart in charts) {
            string += "$chart\n"
        }
        return string
    }
}