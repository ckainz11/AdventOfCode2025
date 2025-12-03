package days.day3

import setup.Day

class Day3(override val input: String) : Day<Long>(input) {

    override fun solve1(): Long = solve(2)
    override fun solve2(): Long = solve(12)

    private fun solve(n: Int): Long = input.lines()
        .map { line -> line.map { c -> c.digitToInt().toLong() } }
        .sumOf { bank ->
            (n - 1 downTo 0).fold(0L to 0) { (sum, prevIndex), pos ->
                bank.drop(prevIndex).dropLast(pos).withIndex().maxBy { it.value }.let {
                    sum * 10 + it.value to it.index + 1 + prevIndex
                }
            }.first
        }

}
