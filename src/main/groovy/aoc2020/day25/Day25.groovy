package aoc2020.day25

import aoc2020.Day
import aoc2020.common.Util

class Day25 extends Day {

	static final long DIVISOR = 20201227
	
	@Override
	public Object calculateResult(Object fileName) {
		List lines = Util.readFile(fileName)
		
		long cardKey = lines[0] as Long
		
		BigInteger subjectNumber = 1
		BigInteger subjectNumberBase = 7
		
		int loopSize = 0
		while(subjectNumber != cardKey) {
			subjectNumber *= subjectNumberBase
			subjectNumber %= DIVISOR
			loopSize++
		}
		
		subjectNumber = 1
		subjectNumberBase = lines[1] as Long
		loopSize.times {
			subjectNumber *= subjectNumberBase
			subjectNumber %= DIVISOR
		}
		
		subjectNumber
	}

	@Override
	public Object calculateResult2(Object fileName) {
		":)"
	}
}
