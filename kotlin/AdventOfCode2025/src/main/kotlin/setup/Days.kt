package setup

import days.day1.Day1
import days.day2.Day2
import days.day3.Day3
import days.day4.Day4


object Days {

	val days = mapOf(
        1 to ::Day1,
        2 to ::Day2,
        3 to ::Day3,
        4 to ::Day4
	)
}
