package days.day4

import setup.Day
import util.Matrix
import util.Point
import util.asMatrix
import util.getOrElse
import util.mapMatrixIndexedNotNull

const val PAPER = '@'
const val THRESHHOLD = 4

class Day4(override val input: String) : Day<Int>(input) {

    private val grid = input.asMatrix()
    private val removed = mutableSetOf<Point>()

    override fun solve1(): Int = grid.getRemovablePaper().size

    override fun solve2(): Int = generateSequence { grid.getRemovablePaper().also { removed.addAll(it) } }
        .takeWhile { it.isNotEmpty() }
        .sumOf { it.size }

    private fun Matrix<Char>.getRemovablePaper() = this
        .mapMatrixIndexedNotNull { p, c -> if (c == PAPER && p.removable()) p else null }

    private fun Point.removable() = !removed.contains(this) && this.allNeighbors()
        .filter { !removed.contains(it) }
        .count { grid.getOrElse(it, '.') == PAPER } < THRESHHOLD
}
