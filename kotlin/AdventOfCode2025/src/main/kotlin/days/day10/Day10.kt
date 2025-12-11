package days.day10

import setup.Day
import util.ImplicitGraph
import util.ImplicitNode
import util.allInts

val lightsRegex = """\[([.#])+\]""".toRegex()
val buttonRegex = """\([\d,]+\)""".toRegex()
val joltageRegex = """\{.*\}""".toRegex()

class Day10(override val input: String) : Day<Int>(input) {

    override fun solve1(): Int = input.lines().sumOf { line ->
		val indicator = lightsRegex.find(line)!!.value
			.drop(1)
			.dropLast(1)

		val target = indicator
			.reversed()
			.mapIndexed { index, c -> if (c == '#') (1 shl index) else 0 }
			.sum()

		val buttonWiring = buttonRegex.findAll(line)
			.map { match -> match.value.allInts().map { 1 shl (indicator.length - it - 1) }.toSet() }
			.toList()

		val start = IndicatorMachine(0,0, buttonWiring)
		val graph = ImplicitGraph.withStartNode(start)
		graph.shortestPathTo(target)
	}

    override fun solve2(): Int {
        TODO("Not yet implemented")
    }

	/**
	 * A machine that can toggle indicators based on button presses.
	 * @param distance the distance from the start node.
	 * @param key the current state of the indicators.
	 * @param wiring the wiring of the buttons to the indicators.
	 */
	class IndicatorMachine(override var distance: Int = 1, override val key: Int, val wiring: List<Set<Int>>) : ImplicitNode<Int, IndicatorMachine> {
		override fun getAdjacentNodes(): List<IndicatorMachine> = wiring
			.map { buttons -> toggle(buttons) }

		private fun toggle(buttons: Set<Int>): IndicatorMachine {
			val nextState = buttons.fold(key) { acc, bit -> acc xor bit}
			return IndicatorMachine(1, nextState, wiring)
		}

	}
}
