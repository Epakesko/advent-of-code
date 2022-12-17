package com.epakesko.advent.of.code.aoc_2022.day15;

import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day
import groovy.json.JsonSlurper

public class Day15 extends Day{
	@Override
	def calculateResult(fileName){
		List<String> lines = Util.readFile(fileName)
		int row = 2000000
		def pattern =  /Sensor at x=(-?\d+), y=(-?\d+): closest beacon is at x=(-?\d+), y=(-?\d+)/;
		Set<Integer> takenInRow = new HashSet<>()
		Set<Integer> beacons = new HashSet<>()
		lines.each { line ->
			def m = line =~ pattern 
			int sx = m[0][1] as Integer
			int sy = m[0][2] as Integer
			int bx = m[0][3] as Integer
			int by = m[0][4] as Integer

			int manhattan = Math.abs(sx - bx) + Math.abs(sy - by)
			int count = manhattan - Math.abs(sy - row)
			if(count >= 0) ((sx - count)..(sx + count)).each{ takenInRow << it }
			if(by == row) beacons << bx
		}

		takenInRow.minus(beacons).size()
	}
		
	@Override
	def calculateResult2(fileName){
		List<String> lines = Util.readFile(fileName)
		-1
	}
}
