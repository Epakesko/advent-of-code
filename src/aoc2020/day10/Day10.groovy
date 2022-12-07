package aoc2020.day10

import aoc2020.Day
import aoc2020.common.Util

class Day10 extends Day {

	@Override
	public Object calculateResult(Object fileName) {
		List lines = Util.readFileAsInts(fileName)
		
		lines.sort()
		
		int oneDiff = 0
		int threeDiff = 1
		
		if(lines[0] == 1) oneDiff++
		else if(lines[0] == 3) threeDiff++
		
		for(int i = 1; i < lines.size(); i++) {
			int diff = lines[i] - lines[i-1]
			if(diff == 1) oneDiff++
			else if(diff == 3) threeDiff++
		}
		
		return oneDiff * threeDiff
	}

	@Override
	public Object calculateResult2(Object fileName) {
		List lines = Util.readFileAsInts(fileName)
		
		lines.sort()
		lines.push(0)
		
		Map possibilites = [:]
		
		for(int i = lines.size() - 1; i >= 0; i--) {
			int num = lines[i]
			long one = possibilites[num+1]?: 0
			long two = possibilites[num+2]?: 0
			long three = possibilites[num+3]?: 0
			
			possibilites[num] = Math.max(1, one + two + three)
		}
		
		possibilites[0]
	}
}
