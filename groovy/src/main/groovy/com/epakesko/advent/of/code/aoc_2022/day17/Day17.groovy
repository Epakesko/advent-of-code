package com.epakesko.advent.of.code.aoc_2022.day17;

import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day

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
		5000.times {
			//if(it % 1705 == 0) println top
			//if(it == (1585 + 1705)) println top
			int x = 2
			int y = top + 4
			Shape s = Shape.values()[it % 5]
			Piece p = new Piece(s, x, y)
			//if(s == Shape.HORIZONTAL && i == 0) println "$it $top"
			boolean falling = false
			boolean moving = true
			while(moving) {
				if(falling) {
					moving = p.fall(floor)
					if(s == Shape.HORIZONTAL) System.out.print(moving? "v" : "#")
					falling = !falling
				}
				else {
					boolean a = p.wind(wind.charAt(i++), floor)
					if(s == Shape.HORIZONTAL) System.out.print(a? wind.charAt(i - 1) : "|")
					falling = !falling
					if(i == windSize) i = 0
				}
			}
			if(s == Shape.HORIZONTAL) println " $top"
		}
		//println floor
		top
		/*
		for(int j = top; j >=0; j--) {
			floor.each { it ->
				System.out.print(it.contains(j) ? "#" : ".")
			}
			println ""
		}
		*/
	}
	
	@Override
	def calculateResult2(fileName) {
		return -1
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
		int j = 0
		String firstPath
		List<Integer> tops = new ArrayList<>()
		while(true) {
			int x = 2
			int y = top + 4
			Shape s = Shape.values()[j++ % 5]
			Piece p = new Piece(s, x, y)
			boolean falling = false
			boolean moving = true
			String path
			while(moving) {
				if(falling) {
					moving = p.fall(floor)
					if(s == Shape.HORIZONTAL) path += moving? "v" : "#"
					falling = !falling
				}
				else {
					boolean a = p.wind(wind.charAt(i++), floor)
					if(s == Shape.HORIZONTAL) path += a ? wind.charAt(i - 1) : "|"
					falling = !falling
					if(i == windSize) i = 0
				}
			}
			if(s == Shape.HORIZONTAL) {
				if(j == 0) firstPath = path
				else if(path.equals(firstPath)) break
			}
		}
		top
	}
}
