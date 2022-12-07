package com.epakesko.advent.of.code.aoc_2019.Day07

import com.epakesko.advent.of.code.aoc_2019.util.IntCode
import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day

class Day07 extends Day {

	@Override
	public Object calculateResult(Object fileName) {
		List firstInputs = [0, 1, 2, 3, 4]
		int max = 0
		List maxOutput
		
		firstInputs.eachPermutation{ perm ->
			IntCode intCode = new IntCode(fileName, [perm[0], 0])
			def output = intCode.run() as int
			
			intCode = new IntCode(fileName, [perm[1], output])
			output = intCode.run() as int
			
			intCode = new IntCode(fileName, [perm[2], output])
			output = intCode.run() as int
			
			intCode = new IntCode(fileName, [perm[3], output])
			output = intCode.run() as int
			
			intCode = new IntCode(fileName, [perm[4], output])
			output = intCode.run() as int
			
			if(output > max) {
				maxOutput = perm
				max = output
			}
		}
		max
	}
	
	@Override
	public Object calculateResult2(Object fileName) {
		
		List firstInputs = [9, 8, 7, 6, 5]
		int max = 0
		List maxOutput
		
		firstInputs.eachPermutation{ perm ->
			def output = 0
			def loopOutput = 0
			IntCode intCode1 = new IntCode(fileName, [perm[0], output])
			output = intCode1.runUntilOutput() as int
			
			IntCode intCode2 = new IntCode(fileName, [perm[1], output])
			output = intCode2.runUntilOutput() as int
			
			IntCode intCode3 = new IntCode(fileName, [perm[2], output])
			output = intCode3.runUntilOutput() as int
			
			IntCode intCode4 = new IntCode(fileName, [perm[3], output])
			output = intCode4.runUntilOutput() as int
			
			IntCode intCode5 = new IntCode(fileName, [perm[4], output])
			output = intCode5.runUntilOutput() as int
			loopOutput = output
			
			while(true) {
				intCode1.addInput(output)
				output = intCode1.runUntilOutput() as Integer
				if(output == null) break;
				
				intCode2.addInput(output)
				output = intCode2.runUntilOutput() as Integer
				
				intCode3.addInput(output)
				output = intCode3.runUntilOutput() as Integer
				
				intCode4.addInput(output)
				output = intCode4.runUntilOutput() as Integer
				
				intCode5.addInput(output)
				output = intCode5.runUntilOutput() as Integer
				loopOutput = output
			}
				
			if(loopOutput > max) {
				maxOutput = firstInputs
				max = loopOutput
			}
		}
		max
	}
}
