package com.epakesko.advent.of.code.aoc_2019.Day01

import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day

class Day01 extends Day {

	@Override
	public Object calculateResult(Object fileName) {
		List masses = Util.readFileAsInts(fileName)
		
		masses.collect { Math.floor(it / 3) - 2 }.sum()
	}
	
	@Override
	public Object calculateResult2(Object fileName) {
		List masses = Util.readFileAsInts(fileName)
		
		masses.collect {getRequiredFuel(it)}.sum()
	}
	
	private int getRequiredFuel(int mass) {
		int requiredFuel = Math.floor(mass / 3) - 2
		if(requiredFuel > 0) {
			requiredFuel += getRequiredFuel(requiredFuel)
		}
		else requiredFuel = 0
		return requiredFuel
	}
}
