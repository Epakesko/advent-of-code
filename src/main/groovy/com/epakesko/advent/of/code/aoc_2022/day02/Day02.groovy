package com.epakesko.advent.of.code.aoc_2022.day02;

import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day

public class Day02 extends Day{	
	@Override
	def calculateResult(fileName){
		List lines = Util.readFile(fileName)
		lines.sum { line ->
			switch(line) {
				case "A X": return 4
				case "B X": return 1
				case "C X": return 7
				case "A Y": return 8
				case "B Y": return 5
				case "C Y": return 2
				case "A Z": return 3
				case "B Z": return 9
				case "C Z": return 6
			}
		}
	}
	
	@Override
	def calculateResult2(fileName){
		List lines = Util.readFile(fileName)
		lines.sum { line ->
			switch(line) {
				case "A X": return 3
				case "B X": return 1
				case "C X": return 2
				case "A Y": return 4
				case "B Y": return 5
				case "C Y": return 6
				case "A Z": return 8
				case "B Z": return 9
				case "C Z": return 7
			}
		}
	}
}
