fun main() {
    fun checkPull(red: Int, green: Int, blue: Int,
                  redConstraint: Int, greenConstraint: Int, blueConstraint: Int): Boolean {
        return !(red > redConstraint || green > greenConstraint || blue > blueConstraint)
    }

    fun checkGame(game: Game, redConstraint: Int, greenConstraint: Int, blueConstraint: Int): Boolean {
        game.pulls.forEach {
            if (!checkPull(it.red, it.green, it.blue, redConstraint, greenConstraint, blueConstraint)) {
                return false
            }
        }
        return true
    }

    fun part1(input: List<String>): Int {
        val redConstraint = 12
        val greenConstraint = 13
        val blueConstraint = 14
        var sum = 0
        input.forEach {
            val g = Game.fromString(it)
            if (checkGame(g, redConstraint, greenConstraint, blueConstraint)) {
                sum += g.id
            }
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        input.forEach {
            val g = Game.fromString(it)
            val (red, green, blue) = g.getSmallestSubset()
            val power = red * green * blue
            sum += power
        }
        return sum
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)
    check(part2(testInput) == 2286)

    // https://adventofcode.com/2023/day/2/input
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}

class Game(val id: Int, val pulls: List<BagPull>) {
    companion object {
        fun fromString(input: String): Game {
            val parts = input.split(":")
            val id = parts.first()
                .removePrefix("Game ")
                .toInt()
            val pulls = extractPulls(parts.last())
            return Game(id, pulls)
        }

        private fun extractPulls(multiplePulls: String): List<BagPull> {
            return multiplePulls.split(";")
                .map { BagPull.fromString(it) }
        }
    }

    fun getSmallestSubset(): Triple<Int, Int, Int> {
        var red = 0
        var green = 0
        var blue = 0

        pulls.forEach {
            if (it.red > red) {
                red = it.red
            }
            if (it.green > green) {
                green = it.green
            }
            if (it.blue > blue) {
                blue = it.blue
            }
        }
        return Triple(red, green, blue)
    }

    override fun toString(): String {
        return "Game $id: $pulls"
    }
}
class BagPull(var red: Int = 0, var green: Int = 0, var blue: Int = 0) {
    companion object {
        fun fromString(singlePull: String): BagPull {
            val pull = BagPull()
            val balls = singlePull.split(",")
            balls.forEach {
                val oneBall = it.trim().split(" ")
                val count = oneBall.first().toInt()
                val color = oneBall.last()
                when (color) {
                    "red" -> pull.red = count
                    "green" -> pull.green = count
                    "blue" -> pull.blue = count
                }
            }
            return pull
        }
    }

    override fun toString(): String {
        return "[$red red, $green green, $blue blue]"
    }
}