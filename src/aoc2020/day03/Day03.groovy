package aoc2020.day03

import aoc2020.Day
import aoc2020.common.Util

class Day03 extends Day {

	@Override
	public Object calculateResult(Object fileName) {
		def lines = Util.readFile(fileName)
		
		return calculateWithSlope(lines, 3, 1)
	}

	
	
	private long calculateWithSlope(lines, slopeRight, slopeDown){
		
		int horizontalIndex = 0;
		int verticalIndex = 0;
		int numberOfTrees = 0;
		int lineLength = lines[0].size()
		
		lines.each { line ->
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
		def lines = Util.readFile(fileName)
		
		return calculateWithSlope(lines, 1, 1) * calculateWithSlope(lines, 3, 1) * calculateWithSlope(lines, 5, 1) * calculateWithSlope(lines, 7, 1) * calculateWithSlope(lines, 1, 2)
	}
}
