package days.day2

import setup.Day

class Day2(override val input: String) : Day<Long>(input) {

    val pattern1 = Regex("""^(\d+)\1$""")
    val pattern2 = Regex("""^(\d+)\1+$""")

    override fun solve1(): Long = solve(pattern1)
    override fun solve2(): Long = solve(pattern2)

    fun solve(pattern: Regex): Long = input
        .lines()
        .first().split(",")
        .map { range -> range.split("-").let { (min, max) -> min.toLong() to max.toLong() } }
        .sumOf { (min, max) -> (min..max).sumOf { if (pattern.matches("$it")) it else 0 } }
}
