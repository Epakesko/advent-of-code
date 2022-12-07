package aoc2020.day02

import aoc2020.Day
import aoc2020.common.Util

public class Day02 extends Day {

	@Override
	public Object calculateResult(Object fileName) {
		def lineList = Util.readFile(fileName)
		def alma = []
		def valid = 0
		lineList.each { String line ->
			int hyphenIndex = line.indexOf("-")
			int spaceIndex = line.indexOf(" ")
			int secondSpaceIndex = line.indexOf(" ", spaceIndex+1)
			int min = line.substring(0, hyphenIndex) as Integer
			int max = line.substring(hyphenIndex + 1, spaceIndex) as Integer
			CharSequence searchedChar = line.getAt(spaceIndex + 1)
			String pw = line.substring(secondSpaceIndex + 1)
			if(min <= pw.count(searchedChar) && pw.count(searchedChar) <= max) valid++
		}
		return valid;
	}

	@Override
	public Object calculateResult2(Object fileName) {
		def lineList = Util.readFile(fileName)
		def alma = []
		def valid = 0
		lineList.each { String line ->
			int hyphenIndex = line.indexOf("-")
			int spaceIndex = line.indexOf(" ")
			int secondSpaceIndex = line.indexOf(" ", spaceIndex+1)
			int firstPos = line.substring(0, hyphenIndex) as Integer
			int secondPos = line.substring(hyphenIndex + 1, spaceIndex) as Integer
			CharSequence searchedChar = line.getAt(spaceIndex + 1)
			String pw = line.substring(secondSpaceIndex + 1)
			if((pw.getAt(firstPos-1) == searchedChar) != (pw.getAt(secondPos-1) == searchedChar)) valid++
		}
		return valid;
	}
	
}