package com.epakesko.advent.of.code.aoc_2021.day05;

import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day

public class Day05 extends Day{
	private static final String MIN = "min"
	private static final String MAX = "max"
	
	@Override
	def calculateResult(fileName){
		List<String> lines = Util.readFile(fileName)
		
		Map<String, Integer> occurences = new HashMap<>()
		
		lines.each { line ->
			def splitted = line.split(" -> ")
			String from = splitted[0]
			String to = splitted[1]
			def fromSplitted = from.split(",")
			def toSplitted = to.split(",")
			int x1 = fromSplitted[0] as Integer
			int y1 = fromSplitted[1] as Integer
			int x2 = toSplitted[0] as Integer
			int y2 = toSplitted[1] as Integer
			int minX = Math.min(x1, x2)
			int maxX = Math.max(x1, x2)
			int minY = Math.min(y1, y2)
			int maxY = Math.max(y1, y2)
			if(x1 == x2) {
				for(int i = minY; i <= maxY; i++) {
					String point = "$x1 - $i"
					if(!occurences.containsKey(point)) occurences.put(point, 0)
					occurences.put(point, occurences.get(point) + 1)
				}
			}
			else if(y1 == y2) {
				for(int i = minX; i <= maxX; i++) {
					String point = "$i - $y1"
					if(!occurences.containsKey(point)) occurences.put(point, 0)
					occurences.put(point, occurences.get(point) + 1)
				}
			}
		}
		occurences.findAll { it.value > 1 }.size()
	}

	@Override
	def calculateResult2(fileName){
		List<String> lines = Util.readFile(fileName)
		
		Map<String, Integer> occurences = new HashMap<>()
		
		lines.each { line ->
			def splitted = line.split(" -> ")
			String from = splitted[0]
			String to = splitted[1]
			def fromSplitted = from.split(",")
			def toSplitted = to.split(",")
			int x1 = fromSplitted[0] as Integer
			int y1 = fromSplitted[1] as Integer
			int x2 = toSplitted[0] as Integer
			int y2 = toSplitted[1] as Integer
			int minX = Math.min(x1, x2)
			int maxX = Math.max(x1, x2)
			int minY = Math.min(y1, y2)
			int maxY = Math.max(y1, y2)
			
			if(x1 == x2) {
				for(int i = minY; i <= maxY; i++) {
					String point = "$x1 - $i"
					if(!occurences.containsKey(point)) occurences.put(point, 0)
					occurences.put(point, occurences.get(point) + 1)
				}
			}
			else if(y1 == y2) {
				for(int i = minX; i <= maxX; i++) {
					String point = "$i - $y1"
					if(!occurences.containsKey(point)) occurences.put(point, 0)
					occurences.put(point, occurences.get(point) + 1)
				}
			}
			else {
				int j
				int diff
				if(x1 == minX) j = y1
				else j = y2
				if(j == minY) diff = 1
				else diff = -1
				
				for(int i = minX; i <= maxX; i++) {
					String point = "$i - $j"
					if(!occurences.containsKey(point)) occurences.put(point, 0)
					occurences.put(point, occurences.get(point) + 1)
					j += diff
				}
			}
		}
		occurences.findAll { it.value > 1 }.size()
	}
}
