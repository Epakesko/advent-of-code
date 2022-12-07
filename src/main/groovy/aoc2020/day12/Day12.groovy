package aoc2020.day12

import aoc2020.Day
import aoc2020.common.Util

class Day12 extends Day {

	enum Dir {
		NORTH(0),
		SOUTH(180),
		EAST(90),
		WEST(270);
		
		int degree
		
		Dir(degree) {
			this.degree = degree
		}
		
		static Dir getByDegree(degree) {
			if(degree == 0 || degree == 360) return NORTH
			else if(degree == 90 || degree == -270 || degree == 450) return EAST
			else if(degree == 180 || degree == -180 || degree == 540) return SOUTH
			else return WEST
		}
		
		Dir rotate(degree) {
			getByDegree(this.degree + degree)
		}
	}
	
	@Override
	public Object calculateResult(Object fileName) {
		List lines = Util.readFile(fileName)
		
		int east = 0
		int north = 0
		
		Dir dir = Dir.EAST
		
		lines.each { String line ->
			char command = line.charAt(0)
			int param = line.substring(1) as Integer
			
			if(command == 'F') {
				switch(dir) {
					case Dir.NORTH: north += param; break;
					case Dir.SOUTH: north -= param; break;
					case Dir.EAST: east += param; break;
					case Dir.WEST: east -= param; break;
				}
			}
			else if (command == 'N') north += param
			else if (command == 'S') north -= param
			else if (command == 'E') east += param
			else if (command == 'W') east -= param
			else if (command == 'L') dir = dir.rotate(-param)
			else if (command == 'R') dir = dir.rotate(param)
		}
		
		return Math.abs(east) + Math.abs(north)
	}

	@Override
	public Object calculateResult2(Object fileName) {
		List lines = Util.readFile(fileName)
		
		int east = 0
		int north = 0
		int wpEast = 10
		int wpNorth = 1
		
		lines.each { String line ->
			char command = line.charAt(0)
			int param = line.substring(1) as Integer
			
			if(command == 'F') {
				east += param * wpEast
				north += param * wpNorth
			}
			else if (command == 'N') wpNorth += param
			else if (command == 'S') wpNorth -= param
			else if (command == 'E') wpEast += param
			else if (command == 'W') wpEast -= param
			else if (command == 'L') {
				(param/90).times { 
					int temp = wpEast
					wpEast = -wpNorth
					wpNorth = temp
				}
			}
			else if (command == 'R') {
				(param/90).times { 
					int temp = wpEast
					wpEast = wpNorth
					wpNorth = -temp
				}
			}
		}
		
		return Math.abs(east) + Math.abs(north)
	}
}
