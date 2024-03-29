package com.epakesko.advent.of.code.aoc_2020.day02

import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day

public class Day02 extends Day {

	@Override
	public Object calculateResult(Object fileName) {
		List lineList = Util.readFile(fileName)
		
		lineList.count { String line ->
			int hyphenIndex = line.indexOf("-")
			int spaceIndex = line.indexOf(" ")
			int secondSpaceIndex = line.indexOf(" ", spaceIndex+1)
			int min = line.substring(0, hyphenIndex) as Integer
			int max = line.substring(hyphenIndex + 1, spaceIndex) as Integer
			CharSequence searchedChar = line.getAt(spaceIndex + 1)
			String pw = line.substring(secondSpaceIndex + 1)
			
			min <= pw.count(searchedChar) && pw.count(searchedChar) <= max
		}
	}

	@Override
	public Object calculateResult2(Object fileName) {
		List lineList = Util.readFile(fileName)
		
		lineList.count { String line ->
			int hyphenIndex = line.indexOf("-")
			int spaceIndex = line.indexOf(" ")
			int secondSpaceIndex = line.indexOf(" ", spaceIndex+1)
			int firstPos = line.substring(0, hyphenIndex) as Integer
			int secondPos = line.substring(hyphenIndex + 1, spaceIndex) as Integer
			CharSequence searchedChar = line.getAt(spaceIndex + 1)
			String pw = line.substring(secondSpaceIndex + 1)
			(pw.getAt(firstPos-1) == searchedChar) != (pw.getAt(secondPos-1) == searchedChar)
		}
	}
	
}