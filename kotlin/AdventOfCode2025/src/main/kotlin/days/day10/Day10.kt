package days.day10

import com.microsoft.z3.Context
import com.microsoft.z3.IntExpr
import com.microsoft.z3.IntNum
import com.microsoft.z3.Status
import setup.Day
import util.ImplicitGraph
import util.ImplicitNode
import util.allInts

val indicatorRegex = """\[([.#])+]""".toRegex()
val buttonRegex = """\([\d,]+\)""".toRegex()
val joltageRegex = """\{.*}""".toRegex()

class Day10(override val input: String) : Day<Int>(input) {

    override fun solve1(): Int = input.lines().sumOf { line ->
		val indicator = indicatorRegex.find(line)!!.value
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

    override fun solve2(): Int = input.lines().sumOf { line ->
		val buttonWiring = buttonRegex.findAll(line)
			.map { match -> match.value.allInts().toSet() }
			.toList()

		val targetJoltages = joltageRegex.find(line)!!.value.allInts()

		solveJoltages(buttonWiring, targetJoltages)
	}

	fun solveJoltages(wiring: List<Set<Int>>, targetJoltages: List<Int>) = Context().use { ctx ->
		val solver = ctx.mkOptimize()
		val zero = ctx.mkInt(0)

		val buttons = wiring.indices
			.map { ctx.mkIntConst("button#$it") }
			.onEach { button -> solver.Add(ctx.mkGe(button, zero)) }
			.toTypedArray()

		targetJoltages.forEachIndexed { counter, targetValue ->
			val buttonsThatIncrement = wiring
				.withIndex()
				.filter { (_, counters) -> counter in counters }
				.map { buttons[it.index] }
				.toTypedArray()

			val target = ctx.mkInt(targetValue)
			val sumOfPresses = ctx.mkAdd(*buttonsThatIncrement) as IntExpr
			solver.Add(ctx.mkEq(sumOfPresses, target))
		}

		val presses = ctx.mkIntConst("presses")
		solver.Add(ctx.mkEq(presses, ctx.mkAdd(*buttons)))
		solver.MkMinimize(presses)

		if (solver.Check() != Status.SATISFIABLE) error("No solution found")
		solver.model.evaluate(presses, false).let { it as IntNum }.int
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
