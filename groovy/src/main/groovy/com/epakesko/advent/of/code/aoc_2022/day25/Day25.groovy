package com.epakesko.advent.of.code.aoc_2022.day25;

import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day

public class Day25 extends Day {
	
	@Override
	def calculateResult(fileName){
		List<String> lines = Util.readFile(fileName)
		println fromSnafu("111-2=0220-00=2")
		toSnafu(lines.sum {
			fromSnafu(it)
		})
	}
	
	long fromSnafu(String snafuNumber) {
		long multiplier = Math.pow(5, snafuNumber.size() - 1)
		long sum = 0
		snafuNumber.each {
			if(it.equals("=")) sum -= 2*multiplier
			else if(it.equals("-")) sum -= multiplier
			else sum += multiplier * (it as Integer)
			multiplier /= 5
		}
		sum
	}
	String toSnafu(long number) {
		long mod = 5
		String snafu = ""
		while(number > 0) {
			long digit = number % mod
			digit /= (mod / 5)
			if(digit == 4) {
				snafu += "-"
				number += (mod / 5)
			}
			else if(digit == 3) {
				snafu += "="
				number += (mod / 5) * 2
			}
			else {
				snafu += digit
				number -= (mod / 5) * digit
			}
			mod *= 5
		}
		snafu.reverse()
	}
	
	@Override
	def calculateResult2(fileName) {
		List<String> lines = Util.readFile(fileName)
		-1
	}
}
