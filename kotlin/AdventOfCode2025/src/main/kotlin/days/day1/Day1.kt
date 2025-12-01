package days.day1

import setup.Day
import util.firstInt
import java.lang.Math.floorDiv
import kotlin.math.abs

class Day1(override val input: String) : Day<Int>(input) {

    private val INITIAL_DIAL = 50
    private val INITIAL_PASSWORD = 0

    override fun solve1(): Int = solve { dial, _ -> if (dial == 0) 1 else 0 }
    override fun solve2(): Int = solve { dial, distance -> rotate(dial, dial + distance) }

    private fun solve(passwordUpdate: (Int, Int) -> Int): Int = parseInput()
        .fold(Pair(INITIAL_PASSWORD, INITIAL_DIAL)) { (password, dial), distance ->
            val newDial = (dial + distance) % 100
            val newPassword = password + passwordUpdate(dial, distance)

            newPassword to newDial
        }.first

    private fun rotate(dial: Int, distance: Int): Int = when {
        dial < distance -> abs(floorDiv(distance, 100) - floorDiv(dial, 100))
        dial > distance -> rotate(-dial, -distance)
        else -> 0
    }

    private fun parseInput(): List<Int> = input.lines()
        .map { it.firstInt() * (if (it[0] == 'L') -1 else 1) }
}
