package days.day5

import setup.Day
import util.adjoint
import util.allLongs
import util.merge
import util.overlaps

class Day5(override val input: String) : Day<Long>(input) {

    private val parsed = input.split("\n\n").let { (fresh, available) ->
        fresh.lines().map { range -> range.split("-").let { (start, end) -> start.toLong()..end.toLong() } } to
                available.allLongs()
    }

    private val freshIngredientRanges = parsed.first
    private val availableIngredients = parsed.second

    override fun solve1(): Long =
        availableIngredients.count { id -> freshIngredientRanges.any { range -> range.contains(id) } }.toLong()

    override fun solve2(): Long = freshIngredientRanges
        .fold(mutableSetOf<LongRange>()) { seen, range -> range.mergeInto(seen) }
        .sumOf { it.last - it.first + 1 }

    private fun LongRange.mergeInto(seen: MutableSet<LongRange>): MutableSet<LongRange> = seen
        .filter { it overlaps this || it adjoint this }
        .toMutableSet()
        .let { toMerge ->
            if (toMerge.isEmpty()) seen.apply { add(this@mergeInto) }
            else seen.apply {
                toMerge.apply { add(this@mergeInto) }.merge()
                    .let { merged -> seen.apply { removeAll(toMerge); add(merged) } }
            }
        }
}
