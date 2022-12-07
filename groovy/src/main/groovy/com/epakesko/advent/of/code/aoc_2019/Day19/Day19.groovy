package com.epakesko.advent.of.code.aoc_2019.Day19

import com.epakesko.advent.of.code.aoc_2019.util.IntCode
import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day
import java.awt.Point

class Day19 extends Day {	
	@Override
	public Object calculateResult(Object fileName) {
		IntCode intCode =  new IntCode(fileName)
		int affectedPoints = 0
		for(int i = 0; i < 50; i++) {
			for(int j = 0; j < 50; j++) {
				intCode.reset()
				intCode.addInput(i)
				intCode.addInput(j)
				affectedPoints += intCode.runUntilOutput() 
			}
		}
		affectedPoints
	}
	
	@Override
	public Object calculateResult2(Object fileName) {
		int i = 0
		int j = 100
		IntCode intCode =  new IntCode(fileName)
		while(true) {
			intCode.reset()
			intCode.addInput(i)
			intCode.addInput(j)
			if(intCode.runUntilOutput() ) {
				intCode.reset()
				intCode.addInput(i+99)
				intCode.addInput(j)
				if(intCode.runUntilOutput() ) {
					intCode.reset()
					intCode.addInput(i)
					intCode.addInput(j+99)
					if(intCode.runUntilOutput() ) return i*10000 + j
					else i++
				}
				else j++
			}
			else i++
		}
	}
}
