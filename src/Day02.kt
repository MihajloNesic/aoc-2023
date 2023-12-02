fun main() {
    fun part1(input: List<String>): Int {
        return input.map { Game.fromString(it) }
            .sumOf { if (it.withinConstraints(12, 13, 14)) it.id else 0 }
    }

    fun part2(input: List<String>): Int {
        return input.map { Game.fromString(it).smallestSubset() }
            .sumOf { it.reduce { acc, i -> acc * i } }
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)
    check(part2(testInput) == 2286)

    // https://adventofcode.com/2023/day/2/input
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}

class Game(val id: Int, private val pulls: List<BagPull>) {
    companion object {
        fun fromString(input: String): Game {
            val parts = input.split(":")
            val id = parts.first()
                .removePrefix("Game ")
                .toInt()
            val pulls = parts.last()
                .split(";")
                .map { BagPull.fromString(it) }
            return Game(id, pulls)
        }
    }

    fun withinConstraints(redConstraint: Int, greenConstraint: Int, blueConstraint: Int): Boolean {
        pulls.forEach {
            if (it.red > redConstraint || it.green > greenConstraint || it.blue > blueConstraint) {
                return false
            }
        }
        return true
    }

    fun smallestSubset(): List<Int> {
        var red = 0
        var green = 0
        var blue = 0
        pulls.forEach {
            red = it.red.coerceAtLeast(red)
            green = it.green.coerceAtLeast(green)
            blue = it.blue.coerceAtLeast(blue)
        }
        return listOf(red, green, blue)
    }
}
class BagPull(var red: Int = 0, var green: Int = 0, var blue: Int = 0) {
    companion object {
        fun fromString(singlePull: String): BagPull {
            val pull = BagPull()
            val balls = singlePull.split(",")
            balls.forEach {
                val (count, color) = it.trim().split(" ")
                when (color) {
                    "red" -> pull.red = count.toInt()
                    "green" -> pull.green = count.toInt()
                    "blue" -> pull.blue = count.toInt()
                }
            }
            return pull
        }
    }
}