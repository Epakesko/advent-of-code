package aoc2019.day1901

import aoc2019.Day2019
import aoc2020.common.Util

class Day1901 extends Day2019 {

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
