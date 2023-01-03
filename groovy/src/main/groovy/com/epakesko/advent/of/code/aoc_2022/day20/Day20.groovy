package com.epakesko.advent.of.code.aoc_2022.day20;

import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day

public class Day20 extends Day{
	
	@Override
	def calculateResult(fileName){
		List<String> lines = Util.readFile(fileName)
		List<String> originalOrder = new ArrayList<>()
		int size = lines.size()
		Map<String, Integer> numberOfOccurences = new HashMap<>();
		lines = lines.collect { line ->
			if(!numberOfOccurences.containsKey(line)) numberOfOccurences.put(line, 1)
			else numberOfOccurences[line]++
			String newLine = "${line}_${numberOfOccurences[line]}"
			originalOrder << newLine
			newLine
		}
		
		originalOrder.each {
			int num = it.split("_")[0] as Integer
			if(num % (size - 1) == 0) return
			int idx = lines.indexOf(it)
			lines.removeAt(idx)
			idx += num
			idx = idx % (size - 1)
			if(idx < 0) {
				idx = size + idx - 1
			}
			lines.add(idx, it)
		}
		int zeroIndex = lines.indexOf("0_1")
		(lines[(zeroIndex + 1000) % size].split("_")[0] as Integer) + (lines[(zeroIndex + 2000) % size].split("_")[0] as Integer) + (lines[(zeroIndex + 3000) % size].split("_")[0] as Integer)
	}
	
	@Override
	def calculateResult2(fileName) {
		List<String> lines = Util.readFile(fileName)
		List<String> originalOrder = new ArrayList<>()
		int size = lines.size()
		Map<String, Integer> numberOfOccurences = new HashMap<>();
		lines = lines.collect { line ->
			if(!numberOfOccurences.containsKey(line)) numberOfOccurences.put(line, 1)
			else numberOfOccurences[line]++
			long num = 811589153 * (line as Long)
			String newLine = "${num}_${numberOfOccurences[line]}"
			originalOrder << newLine
			newLine
		}
		
		10.times {
			originalOrder.each {
				long num = it.split("_")[0] as Long
				if(num % (size - 1) == 0) return
				long idx = lines.indexOf(it)
				lines.removeAt(idx as Integer)
				idx += num
				idx = idx % (size - 1)
				if(idx < 0) {
					idx = size + idx - 1
				}
				lines.add(idx as Integer, it)
			}
		}
		int zeroIndex = lines.indexOf("0_1")
		(lines[(zeroIndex + 1000) % size].split("_")[0] as Long) + (lines[(zeroIndex + 2000) % size].split("_")[0] as Long) + (lines[(zeroIndex + 3000) % size].split("_")[0] as Long)
	}
}
