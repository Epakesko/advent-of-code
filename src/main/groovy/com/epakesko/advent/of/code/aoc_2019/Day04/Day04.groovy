package com.epakesko.advent.of.code.aoc_2019.Day04

import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day

class Day04 extends Day {

	@Override
	public Object calculateResult(Object fileName) {
		List range = Util.readFileAsInts(fileName)
		int start = range[0]
		int end = range[1]

		int count = 0
		for(int i = start; i <= end; i++) {
			if(checkNumber(i)) count++
		}
		count
	}

	private boolean checkNumber(int num) {
		def a = is6Digits(num) 
		def b = hasPair(num)
		def c = doesNotDecrease(num)
		
		a && b && c
	}

	private boolean checkNumber2(int num) {
		def a = is6Digits(num) 
		def b = hasPair2(num)
		def c = doesNotDecrease(num)
		
		a && b && c
	}
	
	private boolean is6Digits(int num) {
		100000 <= num && num <= 999999
	}
	
	private boolean hasPair(int num) {
		int prev = -1
		boolean good = false
		6.times { 
			if(num % 10 == prev) good = true
			prev = num % 10
			num /= 10
		}
		good
	}
	
	private boolean hasPair2(int num) {
		int prev = -1
		def good = false
		def count = 0
		6.times { 
			if(num % 10 == prev) {
				count++
			}
			else {
				if(count == 1) good = true
				count = 0
			}
			
			prev = num % 10
			num /= 10
		}
		if(count == 1) good = true
		good
	}
	
	private boolean doesNotDecrease(int num) {
		int prev = 10
		boolean good = true
		6.times { 
			if(num % 10 > prev) good = false
			prev = num % 10
			num /= 10
		}
		good
	}
	
	@Override
	public Object calculateResult2(Object fileName) {
		List range = Util.readFileAsInts(fileName)
		int start = range[0]
		int end = range[1]

		int count = 0
		for(int i = start; i <= end; i++) {
			if(checkNumber2(i)) count++
		}
		count
	}
}
