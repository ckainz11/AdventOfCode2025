package days.day10

import setup.Day
import util.allInts

val lightsRegex = """\[([.#])+\]""".toRegex()
val buttonRegex = """\([\d,]+\)""".toRegex()
val joltageRegex = """\{.*\}""".toRegex()

class Day10(override val input: String) : Day<Int>(input) {

	private val machines = input.lines().map {line ->
		val machine = IndicatorMachine.fromLine(line)
		val buttonWiring = buttonRegex.findAll(line)
			.map { match -> match.value.allInts().map { 1 shl (machine.numOfLights - it - 1) }.toSet() }
			.toList()

		machine to buttonWiring
	}

    override fun solve1(): Int = machines
		.sumOf { (machine, wiring) -> machine.getFewestPresses(wiring) }

    override fun solve2(): Int {
        TODO("Not yet implemented")


    }


	/**
	 * Represents a machine with a target configuration of lights,
	 * a list of buttons that can toggle certain lights, and
	 * a list of requirements (joltage ratings).
	 *
	 * The target is represented as an Int where each bit corresponds to a light (1 for on, 0 for off).
	 */
	data class IndicatorMachine(val target: Int, val numOfLights: Int) {

		var state = 0

		constructor(state: Int, target: Int, numOfLights: Int) : this(target, numOfLights) {
			this.state = state
		}

		companion object {
			fun fromLine(line: String): IndicatorMachine {
				val indicator = lightsRegex.find(line)!!.value
					.drop(1)
					.dropLast(1)

				val target = indicator
					.reversed()
					.mapIndexed { index, c -> if (c == '#') (1 shl index) else 0 }
					.sum()

				return IndicatorMachine(target, indicator.length)
			}
		}

		fun getFewestPresses(buttonWiring: List<Set<Int>>): Int {
			val visited = mutableSetOf<Int>()
			val queue = ArrayDeque<Pair<IndicatorMachine, Int>>()
			queue.add(this to 0)

			while (queue.isNotEmpty()) {
				val (currentMachine, presses) = queue.removeFirst()

				if (currentMachine.state == currentMachine.target)
					return presses

				if (currentMachine.state in visited) continue
				visited.add(currentMachine.state)

				queue.addAll(currentMachine.getNextStates(buttonWiring).map { it to presses + 1 })
			}

			return 0
		}

		private fun getNextStates(buttonWiring: List<Set<Int>>) = buttonWiring.map { buttons -> toggle(buttons) }

		override fun toString(): String {
			return "Machine(target=${target.toString(2)}, state=${state.toString(2)})"
		}

		/**
		 * Toggles the lights corresponding to the given buttons.
		 * Returns a new Machine instance with the updated state.
		 */
		private fun toggle(buttons: Set<Int>): IndicatorMachine {
			val nextState = buttons.fold(state) { acc, bit -> acc xor bit}
			return IndicatorMachine(nextState, target, numOfLights)
		}
	}
}
