package com.epakesko.advent.of.code.aoc_2022.day14;

import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day
import groovy.json.JsonSlurper

public class Day14 extends Day{
	
	int deepestPoint = 0
	
	private void addLine(Set squares, String start, String end) {
		def startSplitted = start.split(",").collect { it as Integer }
		def endSplitted = end.split(",").collect { it as Integer }

		if(startSplitted[0] == endSplitted[0]) {		
			(startSplitted[1]..endSplitted[1]).each {
				if(it > deepestPoint) deepestPoint = it	
				squares.add("${startSplitted[0]}-$it")
			}
		}
		else {
			(startSplitted[0]..endSplitted[0]).each {
				squares.add("$it-${startSplitted[1]}")
			}
		}
	}
	
	@Override
	def calculateResult(fileName){
		List<String> lines = Util.readFile(fileName)
		Set<String> filledSquares = new HashSet<>()
		lines.each { line ->
			def lineSplitted = line.split(" -> ")
			for(int i = 0; i < lineSplitted.size() - 1; i++) {
				addLine(filledSquares, lineSplitted[i], lineSplitted[i + 1])
			}
		}
		for(int i = 0;; i++) {
			int x = 500
			int y = 0
			while(y <= deepestPoint) {
				if(!filledSquares.contains("$x-${y+1}")) y++
				else if(!filledSquares.contains("${x-1}-${y+1}")) {
					x--
					y++
				}
				else if(!filledSquares.contains("${x+1}-${y+1}")) {
					x++
					y++
				}
				else {
					filledSquares.add("$x-$y")
					break
				}
			}
			if(y > deepestPoint) return i
		}
		-1
	}
		
	@Override
	def calculateResult2(fileName){
		List<String> lines = Util.readFile(fileName)
		Set<String> filledSquares = new HashSet<>()
		lines.each { line ->
			def lineSplitted = line.split(" -> ")
			for(int i = 0; i < lineSplitted.size() - 1; i++) {
				addLine(filledSquares, lineSplitted[i], lineSplitted[i + 1])
			}
		}
		for(int i = 0;; i++) {
			int x = 500
			int y = 0
			while(true) {
				if(y == deepestPoint + 1) {
					filledSquares.add("$x-$y")
					break
				}
				else if(!filledSquares.contains("$x-${y+1}")) y++
				else if(!filledSquares.contains("${x-1}-${y+1}")) {
					x--
					y++
				}
				else if(!filledSquares.contains("${x+1}-${y+1}")) {
					x++
					y++
				}
				else {
					filledSquares.add("$x-$y")
					break
				}
			}
			if(x == 500 && y == 0) return i + 1
		}
		-1
	}
}
