package aoc2020;

import groovy.time.TimeCategory
import groovy.time.TimeDuration

abstract class Day {
	String dayNumber
	Day() {
		dayNumber = this.getClass().getSimpleName()
	}
	
	void solve() {
		Date timeStart = new Date()
		def part1Solution = calculateResult("src/main/resources/aoc2020/${dayNumber}/input.txt")
		Date timeStop1 = new Date()
		def part2Solution = calculateResult2("src/main/resources/aoc2020/${dayNumber}/input.txt")
		Date timeStop2 = new Date()
		TimeDuration duration1 = TimeCategory.minus(timeStop1, timeStart)
		TimeDuration duration2 = TimeCategory.minus(timeStop2, timeStop1)
		println "  $dayNumber\t\t$part1Solution ($duration1)\t\t$part2Solution ($duration2)"
	}
	
	void run(runType){
		if(runType == "test") test()
		else if(runType == "test2") test2()
		else if(runType == "run") run()
		else if(runType == "run2") run2()
	}

	void test() {
		println calculateResult("src/main/resources/aoc2020/${dayNumber}/testinput.txt")
	}

	void test2() {
		println calculateResult2("src/main/resources/aoc2020/${dayNumber}/testinput.txt")
	}
	
	void run() {
		println calculateResult("src/main/resources/aoc2020/${dayNumber}/input.txt")
	}
	
	void run2() {
		println calculateResult2("src/main/resources/aoc2020/${dayNumber}/input.txt")
	}
	
	abstract def calculateResult(fileName)
	abstract def calculateResult2(fileName)
}