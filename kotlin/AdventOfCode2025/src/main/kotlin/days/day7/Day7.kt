package days.day7

import setup.Day
import util.Point
import util.asMatrix
import util.get
import util.matrixIndexOfFirst

const val START = 'S'
const val SPLITTER = '^'

class Day7(override val input: String) : Day<Long>(input) {

    private val grid = input.asMatrix()
    private val start = grid.matrixIndexOfFirst { it == START }

    override fun solve1(): Long = countSplits(listOf(start))
    override fun solve2(): Long = countTimelines(start) + 1

    fun countSplits(beams: List<Point>): Long {
        val validBeams = beams.filter { it inBoundsOf grid }

        if (validBeams.isEmpty()) return 0

        val nextBeams = validBeams
            .flatMap { if (grid[it].isSplitter()) it.split() else listOf(it + Point.DOWN) }
            .distinct()

        return validBeams.count { grid[it].isSplitter() }.toLong() +
                countSplits(nextBeams)
    }

    private val cache: MutableMap<Point, Long> = mutableMapOf()
    fun countTimelines(beam: Point): Long = cache.getOrPut(beam) {
        if (!(beam inBoundsOf grid)) 0
        else if (grid[beam].isSplitter()) 1 + beam.split().sumOf { countTimelines(it) }
        else countTimelines(beam + Point.DOWN)
    }

    private fun Char.isSplitter() = this == SPLITTER
    private fun Point.split() = listOf(this + Point.DOWN_RIGHT, this + Point.DOWN_LEFT)
}
