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
					if(curChar == "e") hor++
					else if(curChar == "w") hor--
				}
				else if(prevChar == "s") {
					if(curChar == "e") ver--
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
					else if(curChar == "w")	ver++
					prevChar = ""
				}
			}
			
			String pos = "$hor $ver"
			if(flippedTiles.contains(pos)) flippedTiles.remove(pos)
			else flippedTiles << pos
		}
		flippedTiles.size()
	}
	
	static final int MAX_LENGTH = 16
	static final int STEPS = 100
	
	@Override
	public Object calculateResult2(Object fileName) {
		List lines = Util.readFile(fileName)
		boolean[][] floor = new boolean[1 + (MAX_LENGTH + STEPS) * 2][1 + (MAX_LENGTH + STEPS) * 2]
		
		int baseX = (MAX_LENGTH + STEPS)
		int baseY = (MAX_LENGTH + STEPS)
		
		lines.each { String line ->
			int ver, hor = 0
			String prevChar = ""
			
			for(int i = 0; i < line.length(); i++) {
				String curChar = line.getAt(i)
				if(curChar == "s" || curChar == "n") prevChar = curChar
				else if(prevChar == "") {
					if(curChar == "e") hor++
					else if(curChar == "w") hor--
				}
				else if(prevChar == "s") {
					if(curChar == "e") ver--
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
					else if(curChar == "w") ver++
					prevChar = ""
				}
			}
			floor[hor+baseX][ver+baseY] = !floor[hor+baseX][ver+baseY]
		}
		
		STEPS.times {
			boolean[][] newFloor = new boolean[1 + (MAX_LENGTH + STEPS) * 2][1 + (MAX_LENGTH + STEPS) * 2]
			for(int i = 0; i < floor.length; i++) {
				for(int j = 0; j < floor[i].length; j++) {
					int black = 0
					if(j > 0 &&  floor[i][j-1]) black++
					if(i < floor.length - 1 && j < floor[i].length - 1 && floor[i+1][j+1]) black++
					if(i > 0 && j > 0 && floor[i-1][j-1]) black++
					if(j < floor[i].length - 1 && floor[i][j+1]) black++
					if(i > 0 && floor[i-1][j]) black++
					if(i < floor.length - 1 && floor[i+1][j]) black++
					
					if(floor[i][j] && (black == 0 || black > 2)) newFloor[i][j] = false
					else if(!floor[i][j] && black == 2) newFloor[i][j] = true
					else newFloor[i][j] = floor[i][j]
				}
			}
			
			floor = newFloor
		}
		
		floor.toList().collect{it.toList()}.flatten().findAll().size()
	}
}
