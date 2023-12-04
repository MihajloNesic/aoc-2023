import kotlin.math.pow

fun main() {
    fun getCardMatches(input: String): Int {
        val (winningNumbers, scratchedNumbers) = input.split(":").last()
                                                      .replace(Regex("\\s+"), " ").trim()
                                                      .split(" | ").map { it.split(" ").toList() }
        return winningNumbers.intersect(scratchedNumbers).count()
    }

    fun getCardPoints(input: String): Int {
        val matches = getCardMatches(input)
        return 2.0.pow(((matches - 1).toDouble())).toInt()
    }

    fun countCardsWon(allCards: List<String>, reducedCards: List<String>, indexOffset: Int): Int {
        var nextCardCount = 0
        reducedCards.forEachIndexed { index, card ->
            val cardsWon = mutableListOf<String>()
            val matches = getCardMatches(card)
            if (matches > 0) {
                val indexWithOffset = index + indexOffset
                for (j in indexWithOffset + 1..indexWithOffset + matches) {
                    if (j >= allCards.size) {
                        break
                    }
                    cardsWon.add(allCards[j])
                }
                nextCardCount += countCardsWon(allCards, cardsWon, indexWithOffset+1)
            }
        }
        return reducedCards.size + nextCardCount
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { getCardPoints(it) }
    }

    fun part2(input: List<String>): Int {
        return countCardsWon(input, input, 0)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 30)

    // https://adventofcode.com/2023/day/4/input
    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
