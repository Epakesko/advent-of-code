package com.epakesko.advent.of.code.aoc_2019.Day09

import com.epakesko.advent.of.code.aoc_2019.util.IntCode
import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day

class Day09 extends Day {

	@Override
	public Object calculateResult(Object fileName) {
		IntCode intCode = new IntCode(fileName, 1)
		intCode.run()
	}
	
	@Override
	public Object calculateResult2(Object fileName) {
		IntCode intCode = new IntCode(fileName, 2)
		intCode.run()
	}
}
