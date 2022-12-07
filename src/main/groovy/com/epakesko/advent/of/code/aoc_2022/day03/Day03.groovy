package com.epakesko.advent.of.code.aoc_2022.day03;

import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day

public class Day03 extends Day{	
	@Override
	def calculateResult(fileName){
		List lines = Util.readFile(fileName)
		int sum = 0
		lines.each { String line ->
			int mid = line.size() / 2
			int value = line.substring(0, mid).getChars().toList().intersect(line.substring(mid).getChars().toList())[0]
			if(value < 91) value -= 38
			else value -= 96
			sum += value
		}
		sum
	}
	
	@Override
	def calculateResult2(fileName){
		List<String> lines = Util.readFile(fileName)
		int sum = 0
		for(int i = 0; i < lines.size(); i+=3) {
			int value = lines[i].getChars().toList().intersect(lines[i+1].getChars().toList()).intersect(lines[i+2].getChars().toList())[0]
			
			if(value < 91) value -= 38
			else value -= 96
			
			sum += value
		}
		sum
	}
}
