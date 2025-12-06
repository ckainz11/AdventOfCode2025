package days.day6

import setup.Day
import util.Matrix
import util.allLongs
import util.columns
import util.join
import util.split

class Day6(override val input: String) : Day<Long>(input) {

    private val operations = input.lines().last()
        .filter { !it.isWhitespace() }
        .map { it.toOperation() }

    override fun solve1(): Long = input.lines().dropLast(1)
        .map { line -> line.allLongs() }
        .columns()
        .solve()

    override fun solve2(): Long = input.lines().dropLast(1)
        .map { line -> line.toList() }
        .columns(pad = ' ')
        .split { col -> col.all { it.isWhitespace() } }
        .map { problem -> problem.map { col -> col.join().trim().toLong() } }
        .solve()

    private fun List<List<Long>>.solve(): Long = this
        .zip(operations)
        .sumOf { (column, op) -> column.reduce(op) }

    private fun Char.toOperation(): (Long, Long) -> Long = when(this) {
        '*' -> Long::times
        '+' -> Long::plus
        else -> error("Unknown operation: $this")
    }

    private fun Matrix<Char>.columns(pad: Char) =
        this.maxBy { it.size }.indices
            .map { x -> this.map { row -> row.getOrElse(x) { pad } } }
}
