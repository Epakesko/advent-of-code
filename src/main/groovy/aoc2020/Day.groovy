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
		
		String coloredDuration1
		String coloredDuration2
		
		if(duration1.seconds < 1) coloredDuration1 = "\u001B[32m" + duration1 + "\u001B[0m"
		else if(duration1.seconds < 3) coloredDuration1 = "\u001B[33m" + duration1 + "\u001B[0m"
		else coloredDuration1 = "\u001B[31m" + duration1 + "\u001B[0m"
		
		if(duration2.seconds < 1) coloredDuration2 = "\u001B[32m" + duration2 + "\u001B[0m"
		else if(duration2.seconds < 3) coloredDuration2 = "\u001B[33m" + duration2 + "\u001B[0m"
		else coloredDuration2 = "\u001B[31m" + duration2 + "\u001B[0m"
		
		int spaces = 15 - (part1Solution.toString().length())
		
		println "  \u001B[35m${dayNumber}\u001B[0m        $part1Solution ($coloredDuration1)" + (" " * spaces) + "$part2Solution ($coloredDuration2)"
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