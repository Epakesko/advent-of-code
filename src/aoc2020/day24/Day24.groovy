package aoc2020.day24

import aoc2020.Day
import aoc2020.common.Util
import java.awt.Point

class Day24 extends Day {

	@Override
	public Object calculateResult(Object fileName) {
		List lines = Util.readFile(fileName)

		Set flippedTiles = []
		
		lines.each { String line ->
			int ver, hor = 0
			String prevChar = ""
			
			for(int i = 0; i < line.length(); i++) {
				String curChar = line.getAt(i)
				if(curChar == "s" || curChar == "n") prevChar = curChar
				else if(prevChar == "") {
					if(curChar == "e") hor+=2
					else if(curChar == "w") hor-=2
				}
				else if(prevChar == "s") {
					if(curChar == "e") {
						hor++
						ver--
					}
					else if(curChar == "w") {
						hor--
						ver--
					}
					prevChar = ""
				}
				else if(prevChar == "n") {
					if(curChar == "e") {
						hor++
						ver++
					}
					else if(curChar == "w") {
						hor--
						ver++
					}
					prevChar = ""
				}
			}
			
			Point point = new Point(hor, ver)
			if(flippedTiles.contains(point)) flippedTiles.remove(point)
			else flippedTiles << point
		}
		
		flippedTiles.size()
	}
	
	@Override
	public Object calculateResult2(Object fileName) {
		List lines = Util.readFile(fileName)
		boolean[][] floor = new boolean[1001][501]
		
		int baseX = 501
		int baseY = 251
		
		lines.each { String line ->
			int ver, hor = 0
			String prevChar = ""
			
			for(int i = 0; i < line.length(); i++) {
				String curChar = line.getAt(i)
				if(curChar == "s" || curChar == "n") prevChar = curChar
				else if(prevChar == "") {
					if(curChar == "e") hor+=2
					else if(curChar == "w") hor-=2
				}
				else if(prevChar == "s") {
					if(curChar == "e") {
						hor++
						ver--
					}
					else if(curChar == "w") {
						hor--
						ver--
					}
					prevChar = ""
				}
				else if(prevChar == "n") {
					if(curChar == "e") {
						hor++
						ver++
					}
					else if(curChar == "w") {
						hor--
						ver++
					}
					prevChar = ""
				}
			}
			floor[hor+baseX][ver+baseY] = !floor[hor+baseX][ver+baseY]
		}
		
		int blacks = 0
		for(int i = 0; i < floor.length; i++) {
			for(int j = 0; j < floor[i].length; j++) {
				if(floor[i][j]) blacks++
			}
		}
		
		100.times {
			println it
			boolean[][] newFloor = new boolean[1001][501]
			for(int i = 0; i < floor.length; i++) {
				for(int j = 0; j < floor[i].length; j++) {
					int black = 0
					if(i > 0 && j > 0 && floor[i-1][j-1]) black++
					if(i > 0 && j < floor[i].length - 1 && floor[i-1][j+1]) black++
					if(i < floor.length - 1 && j > 0 && floor[i+1][j-1]) black++
					if(i < floor.length - 1 && j < floor[i].length - 1 && floor[i+1][j+1]) black++
					if(i > 1 && floor[i-2][j]) black++
					if(i < floor.length - 2 && floor[i+2][j]) black++
					
					if(floor[i][j] && (black == 0 || black > 2)) newFloor[i][j] = false
					else if(!floor[i][j] && black == 2) newFloor[i][j] = true
					else newFloor[i][j] = floor[i][j]
				}
			}
					
			floor = newFloor
		}
		
		blacks = 0
		for(int i = 0; i < floor.length; i++) {
			for(int j = 0; j < floor[i].length; j++) {
				if(floor[i][j]) blacks++
			}
		}
		blacks
	}
}
