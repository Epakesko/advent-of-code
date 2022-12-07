package com.epakesko.advent.of.code.aoc_2022.day01;

import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day

public class Day01 extends Day{	
	@Override
	def calculateResult(fileName){
		List lines = Util.readFile(fileName)
		lines.join(",").split(",,").collect { it.split(",").sum{it as Integer} }.max()
	}
	
	@Override
	def calculateResult2(fileName){
		List lines = Util.readFile(fileName)
		lines.join(",").split(",,").collect { it.split(",").sum{it as Integer} }.sort().takeRight(3).sum()
	}
}
