package com.epakesko.advent.of.code.aoc_2021.day01;

import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day

public class Day01 extends Day{	
	@Override
	def calculateResult(fileName){
		List intList = Util.readFileAsInts(fileName)
		int prev = Integer.MAX_VALUE
		int result = 0
		intList.each { depth ->
			if(depth > prev) result++
			prev = depth
		}
		result
	}
	
	@Override
	def calculateResult2(fileName){
		List intList = Util.readFileAsInts(fileName)
		int prev1 = Integer.MAX_VALUE / 3
		int prev2 = Integer.MAX_VALUE / 3
		int prev3 = Integer.MAX_VALUE / 3
		int result = 0
		intList.each { depth ->
			if(prev1 + prev2 + prev3 < prev2 + prev3 + depth) result++
			prev1 = prev2
			prev2 = prev3
			prev3 = depth
		}
		result
	}
}
