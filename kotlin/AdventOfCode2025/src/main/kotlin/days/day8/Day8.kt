package days.day8

import setup.Day
import util.Point3
import util.allInts
import util.subsetsOfSize

class Day8(override val input: String) : Day<Long>(input) {

    private val isExample = input.lines().size == 20

    private val junctions = input.lines()
        .map { line -> line.allInts().let { (x, y, z) -> Point3(x, y, z) } }

    private val pairs = junctions.subsetsOfSize(2)
        .map { (a, b) -> a to b }
        .sortedBy { (a, b) -> a.distanceTo(b) }

    override fun solve1(): Long = pairs.take(if (isExample) 10 else 1000)
        .fold(junctions.map { hashSetOf(it) }.toMutableList()) { circuits, (a, b) ->
            merge(circuits, a, b)
            circuits
        }.map { it.size }
        .sortedDescending()
        .take(3)
        .reduce(Int::times)
        .toLong()

    override fun solve2(): Long {
        val circuits = junctions.map { hashSetOf(it) }.toMutableList()
        val iter = pairs.iterator()

        var out = 0L
        while (circuits.size > 1) {
            val (a, b) = iter.next()
            if (merge(circuits, a, b))
                out = a.x.toLong() * b.x.toLong()
        }

        return out
    }

    private fun merge(circuits: MutableList<HashSet<Point3>>, a: Point3, b: Point3): Boolean {
        if (circuits.any { a in it && b in it })
            return false

        val circuitA = circuits.first { a in it && b !in it }
        val circuitB = circuits.first { b in it && a !in it }
        circuitB.addAll(circuitA)
        circuits.remove(circuitA)
        return true
    }

}
