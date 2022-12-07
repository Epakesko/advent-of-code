package com.epakesko.advent.of.code.aoc_2022.day01;

import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day

public class Day01 extends Day{	
	@Override
	def calculateResult(fileName){
		List lines = Util.readFile(fileName)
		int max = 0
		int current = 0
		lines.each { line ->
			if(line.equals("")) {
				if(current > max) max = current
				current = 0
			}
			else {
				current += line as Integer
			}
		}
		if(current > max) max = current
		max
	}
	
	@Override
	def calculateResult2(fileName){
				List lines = Util.readFile(fileName)
		int max1 = 0
		int max2 = 0
		int max3 = 0
		int current = 0
		lines.each { line ->
			if(line.equals("")) {
				if(current > max1) {
					 max3 = max2
					 max2 = max1
					 max1 = current
				}
				else if(current > max2) {
					max3 = max2
					max2 = current
				}
				else if(current > max3) {
					max3 = current
				}
				current = 0
			}
			else {
				current += line as Integer
			}
		}
		if(current > max1) {
			 max3 = max2
			 max2 = max1
			 max1 = current
		}
		else if(current > max2) {
			max3 = max2
			max2 = current
		}
		else if(current > max3) {
			max3 = current
		}
		max1+max2+max3
	}
}
