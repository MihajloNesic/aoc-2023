fun main() {
    val numbersMap = mapOf(
        "one" to "1",
        "two" to "2",
        "three" to "3",
        "four" to "4",
        "five" to "5",
        "six" to "6",
        "seven" to "7",
        "eight" to "8",
        "nine" to "9"
    )

    fun getCalibrationValue(input: String): Int {
        val first = input.first { c -> c.isDigit() }
        val last = input.last{ c -> c.isDigit() }
        return "$first$last".toInt()
    }

    fun getCalibrationValueReplaceWords(input: String): Int {
        var calibrationText = ""
        input.indices.forEach {
            val c = input[it]
            if (c.isDigit()) {
                calibrationText += c
            }
            else {
                val sub = input.substring(it)
                for ((text, number) in numbersMap) {
                    if (sub.startsWith(text)) {
                        calibrationText += number
                    }
                }
            }
        }
        return getCalibrationValue(calibrationText)
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { getCalibrationValue(it) }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { getCalibrationValueReplaceWords(it) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_part1_test")
    check(part1(testInput) == 142)
    val testInput2 = readInput("Day01_part2_test")
    check(part2(testInput2) == 281)

    // https://adventofcode.com/2023/day/1/input
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
