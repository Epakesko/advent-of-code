package com.epakesko.advent.of.code.aoc_2019.Day11

import com.epakesko.advent.of.code.aoc_2019.util.IntCode
import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day
import java.awt.Point

class Day11 extends Day {
	
	@Override
	public Object calculateResult(Object fileName) {	
		IntCode intCode = null
		Point dir = new Point(0, 1)
		Point pos = new Point(0, 0)
		Map<Point, Integer> colorByCoordinates = new HashMap<>();
		
		int count = 0
		while(true) {
			if(intCode == null) intCode = new IntCode(fileName, 0)
			else intCode.addInput(colorByCoordinates.containsKey(pos) ? colorByCoordinates.get(pos) : 0);
			Integer color = intCode.runUntilOutput()
			if(color == null) break;
			
			Integer changeDir = intCode.runUntilOutput()
			colorByCoordinates.put(pos, color)
			
			if(!changeDir) {
				if(dir.x == 0) {
					dir.x = -dir.y
					dir.y = 0
				} else {
					dir.y = dir.x
					dir.x = 0
				}
			}
			else {
				if(dir.x == 0) {
					dir.x = dir.y
					dir.y = 0
				} else {
					dir.y = -dir.x
					dir.x = 0
				}
			}
			Point pos2 = new Point((pos.x + dir.x) as Integer, (pos.y + dir.y) as Integer)
			pos = pos2
			
		}
		
		colorByCoordinates.size()
	}
	
	@Override
	public Object calculateResult2(Object fileName) {
		IntCode intCode = null
		Point dir = new Point(0, 1)
		Point pos = new Point(0, 0)
		Map<Point, Integer> colorByCoordinates = new HashMap<>();
		int minX, maxX, minY, maxY
		int count = 0
		while(true) {
			if(minX > pos.x) minX = pos.x
			if(minY > pos.y) minY = pos.y
			if(maxX < pos.x) maxX = pos.x
			if(maxY < pos.y) maxY = pos.y
			if(intCode == null) intCode = new IntCode(fileName, 1)
			else intCode.addInput(colorByCoordinates.containsKey(pos) ? colorByCoordinates.get(pos) : 0);
			Integer color = intCode.runUntilOutput()
			if(color == null) break;
			
			Integer changeDir = intCode.runUntilOutput()
			colorByCoordinates.put(pos, color)
			
			if(!changeDir) {
				if(dir.x == 0) {
					dir.x = -dir.y
					dir.y = 0
				} else {
					dir.y = dir.x
					dir.x = 0
				}
			}
			else {
				if(dir.x == 0) {
					dir.x = dir.y
					dir.y = 0
				} else {
					dir.y = -dir.x
					dir.x = 0
				}
			}
			pos = new Point((pos.x + dir.x) as Integer, (pos.y + dir.y) as Integer)
		}
		
		List lines = []
		for(int i = maxY; i >= minY; i--) {
			String line = ""
			for(int j = minX; j <= maxX; j++) {
				Point drawPoint = new Point(j, i)
				if(colorByCoordinates.containsKey(drawPoint) && colorByCoordinates.get(drawPoint)) line += "#"
				else line += " "
			}
			lines << line
		}
		"\n" + lines.join("\n")
	}
}
