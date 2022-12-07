package aoc2020.day15

import aoc2020.Day
import aoc2020.common.Util

class Day15 extends Day {

	@Override
	public Object calculateResult(Object fileName) {
		List startingNumbers = Util.readFile(fileName)[0].split(",").collect{ it as Integer }
		
		List spokenNumbers = []
		
		int timeSinceSpoken
		
		startingNumbers.each { int number ->
			timeSinceSpoken = spokenNumbers.indexOf(number) + 1
			spokenNumbers.push(number)
		}
		
		for(int i = startingNumbers.size(); i < 2020; i++) {
			int number = timeSinceSpoken
			timeSinceSpoken = spokenNumbers.indexOf(number) + 1
			spokenNumbers.push(number)
		}
		
		spokenNumbers[0] 
	}

	@Override
	public Object calculateResult2(Object fileName) {
		List startingNumbers = Util.readFile(fileName)[0].split(",").collect{ it as Integer }
		
		Map spokenNumbers = [:]
		
		int timeSinceSpoken
		
		startingNumbers.eachWithIndex { int number, int idx ->
			
			timeSinceSpoken = spokenNumbers[number] != null? idx - spokenNumbers[number] : 0
			spokenNumbers[number] = idx
		}
		
		for(int i = startingNumbers.size(); i < 29999999; i++) {
			
			int number = timeSinceSpoken
			timeSinceSpoken = spokenNumbers[number] != null? i - spokenNumbers[number] : 0
			spokenNumbers[number] = i
		}
		
		timeSinceSpoken
	}
}
