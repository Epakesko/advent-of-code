package com.epakesko.advent.of.code.aoc_2019.Day02

import com.epakesko.advent.of.code.aoc_2019.util.IntCode
import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day

class Day02 extends Day {

	@Override
	public Object calculateResult(Object fileName) {
		IntCode intCode = new IntCode(fileName)
		intCode.memory.put(1L, 12)
		intCode.memory.put(2L, 2)
		intCode.run()
		intCode.memory[0L]
	}

	@Override
	public Object calculateResult2(Object fileName) {
		for(int i = 0; i < 100; i++) {
			for(int j = 0; j < 100; j++) {
				IntCode intCode = new IntCode(fileName)
				intCode.memory.put(1L, i)
				intCode.memory.put(2L, j)
				intCode.run()
				if(intCode.memory[0L] == 19690720) {
					return i * 100 + j
				}
			}
		}
		-1
	}
}
