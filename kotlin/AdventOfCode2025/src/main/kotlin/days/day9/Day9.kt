package days.day9

import setup.Day
import util.Point
import util.allInts
import util.subsetsOfSize
import kotlin.math.abs

class Day9(override val input: String) : Day<Long>(input) {

    private val redTiles = input.lines()
        .map { line -> line.allInts().let { (x, y) -> Point(x, y) } }

    private val rectangles = redTiles
        .subsetsOfSize(2)
        .map { (a, b) -> Rectangle(a, b) }
        .sortedByDescending { it.area }

    override fun solve1(): Long = rectangles.first().area

    override fun solve2(): Long {
        TODO("Not yet implemented")
    }

    data class Rectangle(val corner1: Point, val corner2: Point) {
        val width: Long = (abs(corner2.x - corner1.x) + 1).toLong()
        val height: Long = (abs(corner2.y - corner1.y) + 1).toLong()
        val area: Long = width * height

    }
}
