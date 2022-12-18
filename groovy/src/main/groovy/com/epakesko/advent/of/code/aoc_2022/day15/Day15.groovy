package com.epakesko.advent.of.code.aoc_2022.day15;

import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day
import groovy.json.JsonSlurper

public class Day15 extends Day{
	@Override
	def calculateResult(fileName){
		return -1
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
		
	class Sensor {
		int x
		int y
		int viewDistance
		
		public Sensor(x, y, viewDistance) {
			this.x = x
			this.y = y
			this.viewDistance = viewDistance
		}
		
		boolean sees(candidateX, candidateY) {
			Math.abs(x - candidateX) + Math.abs(y - candidateY) <= viewDistance
		}
		
		int nextVisible(int candidateX, int candidateY) {
			int manhattan = Math.abs(x - candidateX) + Math.abs(y - candidateY)
			return (manhattan <= viewDistance)? x + manhattan - Math.abs(y - candidateY) + 1 : candidateX
		}
	}
	
	@Override
	def calculateResult2(fileName){
		List<String> lines = Util.readFile(fileName)
		int size = 4000000
		def pattern =  /Sensor at x=(-?\d+), y=(-?\d+): closest beacon is at x=(-?\d+), y=(-?\d+)/;
		List<Sensor> sensors = new ArrayList<>()
		lines.each { line ->
			def m = line =~ pattern 
			int sx = m[0][1] as Integer
			int sy = m[0][2] as Integer
			int bx = m[0][3] as Integer
			int by = m[0][4] as Integer

			sensors << new Sensor(sx, sy, Math.abs(sx - bx) + Math.abs(sy - by))
		}
		
		for(s in sensors) {
			long x1 = s.x
			long x2 = s.x
			long y = s.y - s.viewDistance - 1
			boolean inc = true
			long x = -1
			while(y <= size && y <= s.y + s.viewDistance) {
				if(y > 0 && y <= size) {
					if(x1 > 0 && !sensors.any { it.sees(x1, y) }) {
						x = x1
						break
					}
					if(x2 <= size && !sensors.any { it.sees(x2, y) }) {
						x = x2
						break
					}
				}
				y++
				if(s.x - x1 == s.viewDistance) inc = false
				if(inc) {
					x1--
					x2++
				}
				else {
					x1++
					x2--
				}
			}
			if(x != -1) {
				return 4000000 * x + y
			}
		}
		-1
	}
}
