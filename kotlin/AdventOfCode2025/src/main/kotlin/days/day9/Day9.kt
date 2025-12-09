package days.day9

import setup.Day
import util.Point
import util.allInts
import util.subsetsOfSize
import kotlin.math.min
import kotlin.math.max

class Day9(override val input: String) : Day<Long>(input) {


    private val redTiles = input.lines()
        .map { line -> line.allInts().let { (x, y) -> Point(x, y) } }

    private val rectangles = redTiles
        .subsetsOfSize(2)
        .map { (a, b) -> Rectangle(a, b) }
        .sortedByDescending { it.area }

    private val edges = redTiles
        .plus(redTiles.first())
        .zipWithNext { a, b -> Rectangle(a, b) }
        .toSet()

    override fun solve1(): Long = rectangles.first().area

    override fun solve2(): Long = rectangles.first { it.isValid(edges) }.area

    data class Rectangle(val xRange: LongRange, val yRange: LongRange) {

        constructor(p1: Point, p2: Point) : this(
            xRange = min(p1.x, p2.x).toLong()..max(p1.x, p2.x).toLong(),
            yRange = min(p1.y, p2.y).toLong()..max(p1.y, p2.y).toLong(),
        )

        val width: Long get() = if (xRange.isEmpty()) 0 else xRange.last - xRange.first + 1
        val height: Long get() = if (xRange.isEmpty()) 0 else yRange.last - yRange.first + 1
        val area: Long get() = width * height

        // check if all corners are inside the polygon created by red tiles
        fun isValid(edges: Set<Rectangle>): Boolean {
            val inside = Rectangle(
                xRange = (xRange.first + 1)..<xRange.last,
                yRange = (yRange.first + 1)..<yRange.last,
            )
            return edges.none { it overlaps inside }
        }

        infix fun Rectangle.overlaps(other: Rectangle): Boolean {
            val horizontalOverlap = maxOf(xRange.first, other.xRange.first) <= minOf(xRange.last, other.xRange.last)
            val verticalOverlap = maxOf(yRange.first, other.yRange.first) <= minOf(yRange.last, other.yRange.last)
            return horizontalOverlap && verticalOverlap
        }
    }
}
