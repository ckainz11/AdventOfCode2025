package days.day11

import setup.Day

const val START_PART_ONE = "you"
const val START_PART_TWO = "svr"
const val DAC = "dac"
const val FFT = "fft"
const val END = "out"

class Day11(override val input: String) : Day<Long>(input) {

    private val isExample = input.lines().size < 20
    private val start = if (isExample) START_PART_TWO else START_PART_ONE

    val graph = input.lines()
        .map { line -> line.split(": ") }
        .associate { (from, to) -> from to to.split(" ") }

    override fun solve1(): Long = countPaths(start, END)
    override fun solve2(): Long =
        (countPaths(START_PART_TWO, DAC) * countPaths(DAC, FFT) * countPaths(FFT, END)) +
        (countPaths(START_PART_TWO, FFT) * countPaths(FFT, DAC) * countPaths(DAC, END))

    private fun countPaths(current: String, end: String, cache: MutableMap<String, Long> = mutableMapOf()): Long =
        if (current == end) 1L
        else cache.getOrPut(current) {
            graph[current]?.sumOf { next -> countPaths(next, end, cache) } ?: 0L
        }
}
