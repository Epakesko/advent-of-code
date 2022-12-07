package aoc2019.day1902

import aoc2019.Day2019
import aoc2019.IntCode
import aoc2020.common.Util

class Day1902 extends Day2019 {

	@Override
	public Object calculateResult(Object fileName) {
		IntCode intCode = new IntCode(fileName)
		intCode.memory[1] = 12
		intCode.memory[2] = 2
		intCode.run()
		intCode.memory[0]
	}

	@Override
	public Object calculateResult2(Object fileName) {
		for(int i = 0; i < 100; i++) {
			for(int j = 0; j < 100; j++) {
				IntCode intCode = new IntCode(fileName)
				intCode.memory[1] = i
				intCode.memory[2] = j
				intCode.run()
				if(intCode.memory[0] == 19690720) {
					return i * 100 + j
				}
			}
		}
		-1
	}
}
