package com.epakesko.advent.of.code.aoc_2022.day17;

import java.awt.Shape

import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day
import groovy.json.JsonSlurper

public class Day17 extends Day{
	int top = 0
		
	enum Shape {
		HORIZONTAL,
		CROSS,
		EDGES,
		VERTICAL,
		SQUARE
	}
	
	class Piece {
		Shape shape
		int x
		int y
		
		public Piece(s, x, y) {
			this.shape = s
			this.x = x
			this.y = y
		}
		
		boolean fall(List<Set> floor) {
			switch(shape) {
				case Shape.HORIZONTAL:
					if(!floor[x].contains(y - 1) && !floor[x+1].contains(y - 1) && !floor[x+2].contains(y - 1) && !floor[x+3].contains(y - 1)) y--
					else {
						floor[x].add(y)
						floor[x+1].add(y)
						floor[x+2].add(y)
						floor[x+3].add(y)
						if(y > top) top = y
						return false
					}
					break
				case Shape.CROSS:
					if(!floor[x].contains(y) && !floor[x+1].contains(y - 1) && !floor[x+2].contains(y)) y--
					else {
						floor[x].add(y+1)
						floor[x+1].add(y)
						floor[x+1].add(y+1)
						floor[x+1].add(y+2)
						floor[x+2].add(y+1)
						if(y + 2 > top) top = y + 2
						return false
					}
					break
				case Shape.EDGES:
					if(!floor[x].contains(y - 1) && !floor[x+1].contains(y - 1) && !floor[x+2].contains(y - 1)) y--
					else {
						floor[x].add(y)
						floor[x+1].add(y)
						floor[x+2].add(y)
						floor[x+2].add(y+1)
						floor[x+2].add(y+2)
						if(y + 2 > top) top = y + 2
						return false
					}
					break
				case Shape.VERTICAL:
					if(!floor[x].contains(y - 1)) y--
					else {
						floor[x].add(y)
						floor[x].add(y+1)
						floor[x].add(y+2)
						floor[x].add(y+3)
						if(y + 3 > top) top = y + 3
						return false
					}
					break
				case Shape.SQUARE:
					if(!floor[x].contains(y - 1) && !floor[x+1].contains(y - 1)) y--
					else {
						floor[x].add(y)
						floor[x].add(y+1)
						floor[x+1].add(y)
						floor[x+1].add(y+1)
						if(y + 1 > top) top = y + 1
						return false
					}
					break
			}
			return true
		}
		
		boolean wind(char c, List<Set> floor) {
			if(c == '<' && x == 0) return false
			
			switch(shape) {
				case Shape.HORIZONTAL:
					if(c == '<') {
						if(floor[x-1].contains(y)) return false
						else x--
					}
					else {
						if(x == 3 || floor[x+4].contains(y)) return false
						else x++
					}
					break
				case Shape.CROSS:
					if(c == '<') {
						if(floor[x-1].contains(y+1) || floor[x].contains(y+2) || floor[x].contains(y)) return false
						else x--
					}
					else {
						if(x == 4 || floor[x+3].contains(y+1) || floor[x+2].contains(y+2) || floor[x+2].contains(y)) return false
						else x++
					}
					break
				case Shape.EDGES:
					if(c == '<') {
						if(floor[x-1].contains(y) || floor[x+1].contains(y+1) || floor[x+1].contains(y+2)) return false
						else x--
					}
					else {
						if(x == 4 || floor[x+3].contains(y) || floor[x+3].contains(y+1) || floor[x+3].contains(y+2)) return false
						else x++
					}
					break
				case Shape.VERTICAL:
					if(c == '<') {
						if(floor[x-1].contains(y) || floor[x-1].contains(y+1) || floor[x-1].contains(y+2) || floor[x-1].contains(y+3)) return false
						else x--
					}
					else {
						if(x == 6 || floor[x+1].contains(y) || floor[x+1].contains(y+1) || floor[x+1].contains(y+2) || floor[x+1].contains(y+3)) return false
						else x++
					}
					break
				case Shape.SQUARE:
					if(c == '<') {
						if(floor[x-1].contains(y) || floor[x-1].contains(y+1)) return false
						else x--
					}
					else {
						if(x == 5 || floor[x+2].contains(y) || floor[x+2].contains(y+1)) return false
						else x++
					}
					break
			}
			return true
		}
	}
	
	@Override
	def calculateResult(fileName){
		List<String> lines = Util.readFile(fileName)
		String wind = lines[0]
		int windSize = wind.size()
		
		List<Set<Integer>> floor = new ArrayList<>()
		7.times { 
			Set start = new HashSet<>()
			start.add(0)
			floor.add(start)
		}
		
		int i = 0
		2022.times { 
			int x = 2
			int y = top + 4
			Shape s = Shape.values()[it % 5]
			Piece p = new Piece(s, x, y)
			boolean falling = false
			boolean moving = true
			while(moving) {
				//if(it == 8) println "$p.x $p.y"
				if(falling) {
					moving = p.fall(floor)
					falling = !falling
				}
				else {
					p.wind(wind.charAt(i++), floor)
					falling = !falling
					if(i == windSize) i = 0
				}
			}
		}
		//println floor
		top
	}
	
	@Override
	def calculateResult2(fileName) {
		List<String> lines = Util.readFile(fileName)
		-1
	}
}
