package com.epakesko.advent.of.code.aoc_2019.Day15

import com.epakesko.advent.of.code.aoc_2019.util.IntCode
import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day
import java.awt.Point

class Day15 extends Day {
	
	@Override
	public Object calculateResult(Object fileName) {	
		IntCode intCode = new IntCode(fileName)
		
		Direction dir = Direction.NORTH
		intCode.addInput(dir.dirCode)
		
		Map<Point, String> tunnels = new HashMap<>()
		Point position = new Point(0, 0)
		tunnels.put(position, 0)
		
		while(true) {
			int status = intCode.continueUntilInput() as Integer
			int shortestRoute = tunnels.get(position)
			
			if(status == 0) {
				dir = dir.turn(true)
			}
			else if(status == 1) {
				position = move(position, dir)
				if(!tunnels.containsKey(position)) tunnels.put(position, shortestRoute + 1) 
				dir = dir.turn(false)
			}
			else {
				return shortestRoute + 1
			}
			
			intCode.addInput(dir.dirCode)
		}
	}
	
	private Point move(Point oldPoint, Direction dir) {
		int x = oldPoint.x
		int y = oldPoint.y
		
		switch(dir) {
			case Direction.NORTH:
				y += 1
				break
			case Direction.SOUTH:
				y -= 1
				break
			case Direction.EAST:
				x += 1
				break
			case Direction.WEST:
				x -= 1
				break
			default: break
		}
		
		return new Point(x, y)
	}
	
	/* DRAW WHOLE MAP VERSION
	@Override
	public Object calculateResult(Object fileName) {		
				IntCode intCode = new IntCode(fileName)
		
		Direction dir = Direction.NORTH
		intCode.addInput(dir.dirCode)
		
		Map<Point, String> tunnels = new HashMap<>()
		Point position = new Point(0, 0)
		tunnels.put(position, "Q")
		
		int minX, maxX, minY, maxY
		
		while(true) {
			int status = intCode.continueUntilInput() as Integer
						
			if(status == 0) {
				tunnels.put(move(position, dir), "#")
				dir = dir.turn(true)
			}
			else if(status == 1) {
				position = move(position, dir)
				tunnels.put(position, ".") 
				dir = dir.turn(false)
			}
			else {
				position = move(position, dir)
				tunnels.put(position, "0")
				dir = dir.turn(false)
			}
			
			if(position.x < minX) minX = position.x
			if(position.x > maxX) maxX = position.x
			if(position.y < minY) minY = position.y
			if(position.y > maxY) maxY = position.y
			
			intCode.addInput(dir.dirCode)
			
			if(position.x == 0 && position.y == 0 && tunnels.size() != 1) break
		}
		tunnels.put(position, "Q")
		
		for(int i = minY; i <= maxY; i++) {
			for(int j = minX; j <= maxX; j++) {
				Point drawPosition = new Point(j, i)
				if(tunnels.containsKey(drawPosition)) print tunnels.get(drawPosition)
				else print " "
			}
			println ""
		}
	} */
	
	@Override
	public Object calculateResult2(Object fileName) {
		IntCode intCode = new IntCode(fileName)
		
		Direction dir = Direction.NORTH
		intCode.addInput(dir.dirCode)
		
		Map<Point, String> tunnels = new HashMap<>()
		Point position = new Point(0, 0)
		
		while(true) {
			int status = intCode.continueUntilInput() as Integer
			
			if(status == 0) {
				dir = dir.turn(true)
			}
			else if(status == 1) {
				position = move(position, dir)
				dir = dir.turn(false)
			}
			else {
				position = move(position, dir)
				break
			}
			
			intCode.addInput(dir.dirCode)
		}
		tunnels.put(position, 0)
		intCode.addInput(dir.dirCode)
		
		while(true) {
			int status = intCode.continueUntilInput() as Integer
			int shortestRoute = tunnels.get(position)
			
			if(status == 0) {
				dir = dir.turn(true)
			}
			else if(status == 1) {
				position = move(position, dir)
				if(!tunnels.containsKey(position)) tunnels.put(position, shortestRoute + 1) 
				dir = dir.turn(false)
			}
			else if(tunnels.size() != 1) {
				break
			}
			
			intCode.addInput(dir.dirCode)
		}
		
		tunnels.values().max()
	}
	
	enum Direction {
		NORTH(1),
		SOUTH(2),
		WEST(3),
		EAST(4);
		
		int dirCode
	
		Direction(int dirCode) {
			this.dirCode = dirCode
		}
		
		Direction turn(boolean dir) {
			if(this == NORTH) return dir? EAST : WEST
			else if(this == EAST) return dir? SOUTH : NORTH
			else if(this == SOUTH) return dir? WEST : EAST
			else if(this == WEST) return dir? NORTH : SOUTH
		}
	}
}
