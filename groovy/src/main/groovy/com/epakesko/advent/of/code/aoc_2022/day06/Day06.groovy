package com.epakesko.advent.of.code.aoc_2022.day06;

import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day

public class Day06 extends Day{
	@Override
	def calculateResult(fileName){
		String line = Util.readFile(fileName)[0]
		List<Character> lastChars = line.substring(0, 4).getChars().toList()
		line.getChars().findIndexOf(4) { Character c ->
			lastChars.add(c)
			lastChars.pop()
			lastChars.toUnique().size() == 4
		} + 1
	}
	
	@Override
	def calculateResult2(fileName){
		String line = Util.readFile(fileName)[0]
		List<Character> lastChars = line.substring(0, 14).getChars().toList()
		line.getChars().findIndexOf(14) { Character c ->
			lastChars.pop()
			lastChars.add(c)
			lastChars.toUnique().size() == 14
		} + 1
	}
}
