package day5

import tools.StringHandler
import kotlin.math.min

/**
 * Class that represents an Almanac that contains the information of the farm.
 */
class Almanac private constructor() {

    val seeds = mutableListOf<Pair<Long, Long>>()

    val seedToSoil = AlmanacMap()
    val soilToFertilizer = AlmanacMap()
    val fertilizerToWater =  AlmanacMap()
    val waterToLight = AlmanacMap()
    val lightToTemperature = AlmanacMap()
    val temperatureToHumidity = AlmanacMap()
    val humidityToLocation = AlmanacMap()

    companion object {
        /**
         * Create an Almanac from the given [filename] [String]
         *
         * @param filename [String] name of the file to be parsed.
         * @return Almanac created from the information in the specified file.
         * @throws IllegalArgumentException if there's no file associated to the given [filename]
         */
        fun from(filename: String): Almanac {
            val almanac = Almanac()
            val data = StringHandler.readFile(filename).lines()

            if (data[0].contains("seed")) {
                val readSeeds = data[0].split(": ")[1].split(' ')
                if (readSeeds.size.mod(2) != 0) {
                    throw IllegalArgumentException()
                }

                for (i in readSeeds.indices step 2) {
                    almanac.seeds.add(Pair(first = readSeeds[i].toLong(), second = readSeeds[i+1].toLong()))
                }
            }

            for (i in data.indices) {
                if (data[i].isEmpty()) {
                    continue
                }

                if (data[i].contains("seed-to-soil")) {
                    readMap(almanac.seedToSoil, data.subList(fromIndex = i + 1, toIndex = data.size))
                }

                if (data[i].contains("soil-to-fertilizer")) {
                    readMap(almanac.soilToFertilizer, data.subList(fromIndex = i + 1, toIndex = data.size))
                }

                if (data[i].contains("fertilizer-to-water")) {
                    readMap(almanac.fertilizerToWater, data.subList(fromIndex = i + 1, toIndex = data.size))
                }

                if (data[i].contains("water-to-light")) {
                    readMap(almanac.waterToLight, data.subList(fromIndex = i + 1, toIndex = data.size))
                }

                if (data[i].contains("light-to-temperature")) {
                    readMap(almanac.lightToTemperature, data.subList(fromIndex = i + 1, toIndex = data.size))
                }

                if (data[i].contains("temperature-to-humidity")) {
                    readMap(almanac.temperatureToHumidity, data.subList(fromIndex = i + 1, toIndex = data.size))
                }

                if (data[i].contains("humidity-to-location")) {
                    readMap(almanac.humidityToLocation, data.subList(fromIndex = i + 1, toIndex = data.size))
                }

            }
            return almanac
        }

        /**
         * Read a map.
         *
         * @param map [AlmanacMap] to store the values read.
         * @param input [List] of [String] to be used as input.
         */
        private fun readMap(map: AlmanacMap, input: List<String>) {
            for (line in input) {
                if (line.isEmpty()) {
                    return
                }
                val values = line.split(' ')
                map.addChart(src = values[1], dst = values[0], length = values[2])
            }
        }
    }

    /**
     * Get the type of soil for a type of [seed].
     *
     * @param seed Seed to obtain the type of soil off of.
     * @return [Long] that represents the type of soil.
     */
    fun getSoil(seed: Long): Long {
        return seedToSoil.getValue(seed)
    }

    /**
     * Get the type of fertilizer needed for a type of [soil].
     *
     * @param soil Soil to obtain the fertilizer off of.
     * @return [Long] that represents the type of fertilizer to be used.
     */
    fun getFertilizer(soil: Long): Long {
        return soilToFertilizer.getValue(soil)
    }

    /**
     * Get the amount of water needed for a type of [fertilizer].
     *
     * @param fertilizer [Long] identifier of the type of fertilizer used.
     * @return [Long] that represents the amount of water needed.
     */
    fun getWater(fertilizer: Long): Long {
        return fertilizerToWater.getValue(fertilizer)
    }

    /**
     * Get the amount of light needed for a given amount of [water].
     *
     * @param water Amount of water needed.
     * @return [Long] that represents the amount of light needed.
     */
    fun getLight(water: Long): Long {
        return waterToLight.getValue(water)
    }

    /**
     * Get the temperature needed given the amount of [light].
     *
     * @param light Amount of light needed.
     * @return [Long] that represents the temperature needed.
     */
    fun getTemperature(light: Long): Long {
        return lightToTemperature.getValue(light)
    }

    /**
     * Get the humidity to maintain given a [temperature].
     *
     * @param temperature Temperature used.
     * @return [Long] that represents the humidity to be maintained.
     */
    fun getHumidity(temperature: Long): Long {
        return temperatureToHumidity.getValue(temperature)
    }

    /**
     * Get the location maintained at the given [humidity]
     *
     * @param humidity Humidity at which the plantation is kept.
     * @return [Long] that represents the location.
     */
    fun getLocation(humidity: Long): Long {
        return humidityToLocation.getValue(humidity)
    }

    /**
     * Get the location where the provided [seed] should be planted.
     *
     * @param seed [Long] that identifies the seed to be planted.
     * @return [Long] that represents the location where [seed] should be planted.
     */
    fun getLocationFromSeed(seed: Long): Long {
        return getLocation(humidity =
            getHumidity(temperature =
                getTemperature(light =
                    getLight(water =
                        getWater(fertilizer =
                            getFertilizer(soil =
                                getSoil(seed = seed)
                            )
                        )
                    )
               )
            )
        )
    }

    override fun toString(): String {
        var string = ""
        string += "Available seeds: $seeds\n"
        string += "seed-to-soil:\n$seedToSoil"
        string += "fertilizer-to-water:\n$fertilizerToWater"
        string += "water-to-light:\n$waterToLight"
        string += "light-to-temperature:\n$lightToTemperature"
        string += "temperature-to-humidity:\n$temperatureToHumidity"
        string += "humidity-to-location:\n$humidityToLocation"
        return string
    }
}

fun main() {
    val almanac = Almanac.from("src\\main\\resources\\almanac.txt")

    println("Seeds: ${almanac.seeds}")

/*    val min = almanac.seeds.fold(initial = Long.MAX_VALUE) { acc: Long, seedRange: Pair<Long, Long> ->
        var min = acc
        for (seed in seedRange.first..< (seedRange.first + seedRange.second)) {
            min = min(min, almanac.getLocationFromSeed(seed))
        }
        min
    }*/

    val locations = almanac.seeds.map {
        var min: Long = Long.MAX_VALUE
        for (seed in it.first..< (it.first + it.second)) {
            min = min(almanac.getLocationFromSeed(seed), min)
        }
        min
    }

    /*println("Locations: $locations")*/
    //println("Min: ${locations.min()}")
    println("Min: ${locations.min()}")
}