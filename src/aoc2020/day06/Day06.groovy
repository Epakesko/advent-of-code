package aoc2020.day06

import aoc2020.Day
import aoc2020.common.Util

class Day06 extends Day {

	@Override
	public Object calculateResult(Object fileName) {
		def list = Util.readFile(fileName)
		def groups = []
		int groupIndex = 0
		list.each { String line ->
			if(line == "") {
				groupIndex++
				return
			}
			if(groups[groupIndex] == null) {
				Set newSet = []
				groups << newSet
			}
			for(char c : line) {
				groups[groupIndex] << c
			}	
		}
		groups.sum { it.size() }
	}

	@Override
	public Object calculateResult2(Object fileName) {
		def list = Util.readFile(fileName)
		def groups = []
		int groupIndex = 0
		boolean newGroupStart = true
		list.each { String line ->
			if(line == "") {
				groupIndex++
				newGroupStart = true
				return
			}
			if(groups[groupIndex] == null) {
				Set newSet = []
				groups << newSet
			}
			Set lineSet = []
			for(char c : line) {
				lineSet << c
			}
			if(newGroupStart) {
				groups[groupIndex] = lineSet
			}
			else {
				groups[groupIndex] = groups[groupIndex].intersect(lineSet)
			}
			newGroupStart = false
		}
		groups.sum { it.size() }
	}
}
