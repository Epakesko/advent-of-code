package com.epakesko.advent.of.code.aoc_2020.day03

import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day

class Day03 extends Day {

	@Override
	public Object calculateResult(Object fileName) {
		List lines = Util.readFile(fileName)
		return calculateWithSlope(lines, 3, 1)
	}
	
	private long calculateWithSlope(List lines, int slopeRight, int slopeDown){
		
		int horizontalIndex = 0;
		int verticalIndex = 0;
		int numberOfTrees = 0;
		int lineLength = lines[0].length()
		
		lines.each { String line ->
			if(verticalIndex++ % slopeDown != 0) return
			if(line.getAt(horizontalIndex) == '#') {
				numberOfTrees++
			}
			horizontalIndex += slopeRight
			if(horizontalIndex >= lineLength) horizontalIndex -= lineLength
		}
		
		return numberOfTrees
	}
	
	@Override
	public Object calculateResult2(Object fileName) {
		List lines = Util.readFile(fileName)
		return calculateWithSlope(lines, 1, 1) * calculateWithSlope(lines, 3, 1) * calculateWithSlope(lines, 5, 1) * calculateWithSlope(lines, 7, 1) * calculateWithSlope(lines, 1, 2)
	}
}
