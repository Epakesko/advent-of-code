package com.epakesko.advent.of.code.aoc_2022.day10;

import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day

public class Day10 extends Day{
	
	int x = 1
	int idx = 1
	int sum = 0
	
	private increase() {
		if(idx % 40 == 20) sum += x * idx
		idx++
	}
	
	@Override
	def calculateResult(fileName){
		List<String> lines = Util.readFile(fileName)
		lines.each { line ->
			if(line.equals("noop")) {
				increase()
			}
			else {
				increase()
				increase()
				x += (line.split(" ")[1] as Integer)
			}
		}
		sum
	}
	
	List<String> crtLines = new ArrayList<>()
	int crtLineIdx = 0
	
	private draw() {
		if(Math.abs(idx - x) <= 1) crtLines[crtLineIdx] += "#"
		else crtLines[crtLineIdx] += "."
		idx++
		if(idx == 40) {
			idx = 0
			crtLineIdx++
		}
	}
	
	@Override
	def calculateResult2(fileName){
		x = 1
		idx = 0
		List<String> lines = Util.readFile(fileName)
		6.times { crtLines.add("") }
		lines.each { line ->
			if(line.equals("noop")) {
				draw();
			}
			else {
				draw();
				draw();
				x += (line.split(" ")[1] as Integer)
			}
		}
		"FPGPHFGH"
	}
}
