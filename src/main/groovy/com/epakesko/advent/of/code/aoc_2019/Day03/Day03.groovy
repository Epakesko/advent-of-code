package com.epakesko.advent.of.code.aoc_2019.Day03

import com.epakesko.advent.of.code.aoc_2019.util.IntCode
import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day

class Day03 extends Day {

	@Override
	public Object calculateResult(Object fileName) {
		List wires = Util.readFile(fileName)
		
		List wire1 = wires[0].split(",")
		List wire2 = wires[1].split(",")
		
		List wire1OnX = []
		List wire1OnY = []
		
		int curX = 0
		int curY = 0
		wire1.each { String pos ->
			String dir = pos[0]
			int dist = pos.substring(1) as int
			if(dir == "R") {
				wire1OnY << [min: curX, max: curX + dist, y: curY]
				curX += dist
			}
			if(dir == "L") {
				wire1OnY << [max: curX, min: curX - dist, y: curY]
				curX -= dist
			}
			if(dir == "U") {
				wire1OnX << [min: curY, max: curY+ dist, x: curX]
				curY += dist
			}
			if(dir == "D") {
				wire1OnX << [max: curY, min: curY - dist, x: curX]
				curY -= dist
			}
		}
		
		long minManhatten = Long.MAX_VALUE;
		
		curX = 0
		curY = 0
		
		wire2.each { String pos ->
			String dir = pos[0]
			int dist = pos.substring(1) as int
			if(dir == "R") {
				wire1OnX.each { wire1Pos ->
					if(wire1Pos.min <= curY && wire1Pos.max >= curY && curX <= wire1Pos.x && wire1Pos.x <= (curX + dist)) {
						if(Math.abs(curY) + Math.abs(wire1Pos.x) < minManhatten) {
							def sum = Math.abs(curY) + Math.abs(wire1Pos.x)
							if(sum != 0) minManhatten = sum
						}
					}
				}
				curX += dist
			}
			if(dir == "L") {
				wire1OnX.each { wire1Pos ->
					if(wire1Pos.min <= curY && wire1Pos.max >= curY && curX >= wire1Pos.x && wire1Pos.x >= (curX - dist)) {
						if(Math.abs(curY) + Math.abs(wire1Pos.x) < minManhatten) {
							def sum = Math.abs(curY) + Math.abs(wire1Pos.x)
							if(sum != 0) minManhatten = sum
						}
					}
				}
				curX -= dist
			}
			if(dir == "U") {
				wire1OnY.each { wire1Pos ->
					if(wire1Pos.min <= curX && wire1Pos.max >= curX && curY <= wire1Pos.y && wire1Pos.y <= (curY + dist)) {
						if(Math.abs(curX) + Math.abs(wire1Pos.y) < minManhatten) {
							def sum = Math.abs(curX) + Math.abs(wire1Pos.y)
							if(sum != 0) minManhatten = sum
						}
					}
				}
				curY += dist
			}
			if(dir == "D") {
				wire1OnY.each { wire1Pos ->
					if(wire1Pos.min <= curX && wire1Pos.max >= curX && curY >= wire1Pos.y && wire1Pos.y >= (curY - dist)) {
						if(Math.abs(curX) + Math.abs(wire1Pos.y) < minManhatten) {
							def sum = Math.abs(curX) + Math.abs(wire1Pos.y)
							if(sum != 0) minManhatten = sum
						}
					}
				}
				curY -= dist
			}
		}
		minManhatten
	}

	@Override
	public Object calculateResult2(Object fileName) {
				List wires = Util.readFile(fileName)
		
		List wire1 = wires[0].split(",")
		List wire2 = wires[1].split(",")
		
		List wire1OnX = []
		List wire1OnY = []
		
		int curX = 0
		int curY = 0
		int length = 0
		wire1.each { String pos ->
			String dir = pos[0]
			int dist = pos.substring(1) as int
			if(dir == "R") {
				wire1OnY << [min: curX, max: curX + dist, y: curY, length: length, right: true]
				curX += dist
				length += dist
			}
			if(dir == "L") {
				wire1OnY << [max: curX, min: curX - dist, y: curY, length: length, right: false]
				curX -= dist
				length += dist
			}
			if(dir == "U") {
				wire1OnX << [min: curY, max: curY+ dist, x: curX, length: length, up: true]
				curY += dist
				length += dist
			}
			if(dir == "D") {
				wire1OnX << [max: curY, min: curY - dist, x: curX, length: length, up: false]
				curY -= dist
				length += dist
			}
		}
		
		long min = Long.MAX_VALUE;
		
		curX = 0
		curY = 0
		length = 0
		
		wire2.each { String pos ->
			String dir = pos[0]
			int dist = pos.substring(1) as int
			if(dir == "R") {
				wire1OnX.each { wire1Pos ->
					if(wire1Pos.min <= curY && wire1Pos.max >= curY && curX <= wire1Pos.x && wire1Pos.x <= (curX + dist)) {
						def wire1Length = wire1Pos.length
						if(wire1Pos.up) wire1Length += curY - wire1Pos.min
						else wire1Length += wire1Pos.max - curY
						
						def wire2Length = length + wire1Pos.x - curX
						
						
						if(wire1Length + wire2Length < min) {
							def sum = wire1Length + wire2Length
							if(sum != 0) min = sum
						}
					}
				}
				curX += dist
				length += dist
			}
			if(dir == "L") {
				wire1OnX.each { wire1Pos ->
					if(wire1Pos.min <= curY && wire1Pos.max >= curY && curX >= wire1Pos.x && wire1Pos.x >= (curX - dist)) {
						def wire1Length = wire1Pos.length
						if(wire1Pos.up) wire1Length += curY - wire1Pos.min
						else wire1Length += wire1Pos.max - curY
						
						def wire2Length = length - wire1Pos.x + curX
						
						
						if(wire1Length + wire2Length < min) {
							def sum = wire1Length + wire2Length
							if(sum != 0) min = sum
						}
					}
				}
				curX -= dist
				length += dist
			}
			if(dir == "U") {
				wire1OnY.each { wire1Pos ->
					if(wire1Pos.min <= curX && wire1Pos.max >= curX && curY <= wire1Pos.y && wire1Pos.y <= (curY + dist)) {
						def wire1Length = wire1Pos.length
						if(wire1Pos.right) wire1Length += curX - wire1Pos.min
						else wire1Length += wire1Pos.max - curX
						
						def wire2Length = length + wire1Pos.y - curY
						
						
						if(wire1Length + wire2Length < min) {
							def sum = wire1Length + wire2Length
							if(sum != 0) min = sum
						}
					}
				}
				curY += dist
				length += dist
			}
			if(dir == "D") {
				wire1OnY.each { wire1Pos ->
					if(wire1Pos.min <= curX && wire1Pos.max >= curX && curY >= wire1Pos.y && wire1Pos.y >= (curY - dist)) {
						def wire1Length = wire1Pos.length
						if(wire1Pos.right) wire1Length += curX - wire1Pos.min
						else wire1Length += wire1Pos.max - curX
						
						def wire2Length = length - wire1Pos.y + curY
						
						
						if(wire1Length + wire2Length < min) {
							def sum = wire1Length + wire2Length
							if(sum != 0) min = sum
						}
					}
				}
				curY -= dist
				length += dist
			}
		}
		min
	}
}
