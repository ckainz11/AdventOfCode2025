package days.day6

import setup.Day
import util.Matrix
import util.allLongs
import util.columns
import util.join
import util.rotated

class Day6(override val input: String) : Day<Long>(input) {

    private val columns = input.lines().dropLast(1).map { it.allLongs() }.columns()
    private val operations = input.lines().last().filter { !it.isWhitespace() }.map { it.toOperation() }

    override fun solve1(): Long = columns.zip(operations)
        .sumOf { (column, op) -> column.reduce(op) }

    override fun solve2(): Long {
        val test = input.lines().dropLast(1).map { it.toList() }.columns(' ')
        var sum = 0L
        var problemCount = 0;
        val nums = mutableListOf<Long>()
        for (col in test)  {
            if (col.all { it.isWhitespace() }) {
                sum += nums.reduce(operations[problemCount])
                problemCount++
                nums.clear()
                continue
            }

            val num = col.join("").trim().toLong()
            nums.add(num)
        }
        sum += nums.reduce(operations[problemCount])
        return sum
    }

    private fun List<Long>.transformRightToLeft(): List<Long> {
        val maxLength = this.maxOf { it.toString().length }
        return emptyList()
    }

    private fun Char.toOperation(): (Long, Long) -> Long = when(this) {
        '*' -> Long::times
        '+' -> Long::plus
        else -> error("Unknown operation: $this")
    }

    private fun Matrix<Char>.columns(pad: Char) =
        this.maxBy { it.size }.indices
            .map { x -> this.map { row -> row.getOrElse(x) { pad } } }
}
