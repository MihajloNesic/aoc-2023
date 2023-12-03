fun main() {
    fun hasAdjacentSymbols(schematic: List<String>, row: Int, col: Int, len: Int): Boolean {
        val x = if (row - 1 < 0) 0 else row - 1
        val y = if ((col - 1) - len < 0) 0 else (col - 1) - len
        for (i in x..row+1) {
            if (i >= schematic.size) {
                break
            }
            for (j in y..col) {
                if (j >= schematic.first().length) {
                    break
                }
                if (!schematic[i][j].isDigit() && schematic[i][j] != '.') {
                    return true
                }
            }
        }
        return false
    }

    fun findGearNumbers(schematic: List<String>, row: Int, col: Int): List<Long> {
        val numbers = mutableListOf<Long>()
        val x = if (row - 1 < 0) 0 else row - 1
        val y = if (col - 1 < 0) 0 else col - 1
        for (i in x..row+1) {
            if (i >= schematic.size) {
                break
            }
            var number = ""

            for (j in y..col+1) {
                if (i == row && j == col && number.isNotEmpty()) {
                    numbers.add(number.toLong())
                    number = ""
                }
                if (j >= schematic.first().length) {
                    break
                }

                if (schematic[i][j].isDigit()) {
                    // check backwards
                    if (j-1 >= 0 && schematic[i][j-1].isDigit() && number.isEmpty()) {
                        var k = j - 1
                        while (k >= 0 && schematic[i][k].isDigit()) {
                            number = schematic[i][k] + number
                            k--
                        }
                    }
                    number += schematic[i][j]
                }
                else if (number.isNotEmpty()) {

                    numbers.add(number.toLong())
                    number = ""
                }
            }
            if (number.isNotEmpty()) {
                // check forwards
                var l = col + 2
                if (l < schematic[i].length && schematic[i][l].isDigit() && number.isNotEmpty()) {
                    while (l < schematic[i].length && schematic[i][l].isDigit()) {
                        number += schematic[i][l]
                        l++
                    }
                }

                numbers.add(number.toLong())
                number = ""
            }
        }
        return numbers
    }

    fun part1(input: List<String>): Long {
        var sum = 0L
        input.forEachIndexed { row, line ->
            var number = ""
            line.forEachIndexed { col, c ->
                if (c.isDigit()) {
                    number += c
                }
                else if (number.isNotEmpty()) {
                    if (hasAdjacentSymbols(input, row, col, number.length)) {
                        sum += number.toLong()
                    }
                    number = ""
                }
            }
            if (number.isNotEmpty()) {
                if (hasAdjacentSymbols(input, row, line.length, number.length)) {
                    sum += number.toLong()
                }
                number = ""
            }
        }
        return sum
    }

    fun part2(input: List<String>): Long {
        var sum = 0L
        input.forEachIndexed { row, line ->
            line.forEachIndexed { col, c ->
                if (c == '*') {
                    val numbers = findGearNumbers(input, row, col)
                    if (numbers.size == 2) {
                        sum += numbers.reduce { acc, i -> acc * i }
                    }
                }
            }
        }
        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361L)
    check(part2(testInput) == 467835L)

    // https://adventofcode.com/2023/day/3/input
    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
