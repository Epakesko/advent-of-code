package aoc2020.day20

import aoc2020.Day
import aoc2020.common.Util

class Day20 extends Day {

	@Override
	public Object calculateResult(Object fileName) {
		List lines = Util.readFile(fileName)
		
		int i = 0
		int tileNum = -1
		
		Map borders = [:]
		
		for(String line = lines[i]; i < lines.size(); line = lines[++i]) {
			if(i % 12 == 0) {
				tileNum = line.split("Tile ")[1].split(":")[0] as Integer
			}
			else if(i % 12 == 11) continue
			else if(i % 12 == 1) {
				borders[tileNum] = []
				borders[tileNum] << line
				borders[tileNum] << line.getAt(0)
				borders[tileNum] << line.getAt(line.length()-1)				
			} 
			else if(i % 12 == 10) {
				borders[tileNum] << line
				borders[tileNum][1] += line.getAt(0)
				borders[tileNum][2] += line.getAt(line.length()-1)	
			}
			else {
				borders[tileNum][1] += line.getAt(0)
				borders[tileNum][2] += line.getAt(line.length()-1)	
			}
		}
		long product = 1
		borders.each { tileNum1, listOfBorders1 ->
			Set hasPair = []
			borders.each { tileNum2, listOfBorders2 ->
				if(tileNum1 == tileNum2) return
				else {
					listOfBorders1.each { String border1 ->
						listOfBorders2.each { String border2 ->
							if(border1.equals(border2) || border1.reverse().equals(border2)) {
								hasPair << border1
							}
						}
					}
				}
			}
			if(hasPair.size() == 2) {
				product *= tileNum1
			}
		}
		
		product
	}

	@Override
	public Object calculateResult2(Object fileName) {
				List lines = Util.readFile(fileName)
		
		int i = 0
		int tileNum = -1
		
		Map borders = [:]
		Map tiles = [:]
		
		for(String line = lines[i]; i < lines.size(); line = lines[++i]) {
			if(i % 12 == 0) tileNum = line.split("Tile ")[1].split(":")[0] as Integer
			else if(i % 12 == 11) continue
			else if(i % 12 == 1) {
				borders[tileNum] = []
				borders[tileNum] << line
				borders[tileNum] << line.getAt(0)
				borders[tileNum] << line.getAt(line.length()-1)				
			} 
			else if(i % 12 == 10) {
				borders[tileNum] << line
				borders[tileNum][1] += line.getAt(0)
				borders[tileNum][2] += line.getAt(line.length()-1)	
			}
			else {
				if(!tiles[tileNum]) tiles[tileNum] = []
				tiles[tileNum] << line.substring(1, line.size()-1)
				borders[tileNum][1] += line.getAt(0)
				borders[tileNum][2] += line.getAt(line.length()-1)	
			}
		}
		
		int bigPicHeight = Math.sqrt(borders.keySet().size())
		int tileNum1 = 3209
		int dirIdx = 3
		int rowIndex = 0
		List results = []
		(bigPicHeight*tiles[tileNum1].size()).times { results << "" }

		tiles[tileNum1].eachWithIndex { String tileLine, int idx ->
			results[idx] += tileLine
		}

		while((results.last().length() < (bigPicHeight * 8)) || (results.first().length() < (bigPicHeight * 8))) {
			boolean done = false
			borders.each { int tileNum2, List listOfBorders ->	
				if(done || tileNum1 == tileNum2) { 
					return
				}
				else {
					listOfBorders.eachWithIndex { String border2, int dirIdx2 ->
						if(done) return
						if(borders[tileNum1][dirIdx].equals(border2)) {
							if(dirIdx + dirIdx2 == 3) {
								tileNum1 = tileNum2
								done = true
							} 
							else if(dirIdx == dirIdx2) {
								List result = flipTile(dirIdx == 1 || dirIdx == 2, borders[tileNum2], tiles[tileNum2])
								borders[tileNum2] = result[0]
								tiles[tileNum2] = result[1]
								tileNum1 = tileNum2
								done = true
							}
							else if((dirIdx2 == 3 && dirIdx == 2) || (dirIdx2 == 2 && dirIdx == 0) || (dirIdx2 == 1 && dirIdx == 3) || (dirIdx2 == 0 && dirIdx == 1)) {
								List result = rotateTile(1, borders[tileNum2], tiles[tileNum2])
								borders[tileNum2] = result[0]
								tiles[tileNum2] = result[1]
								
								if((dirIdx2 == 2 && dirIdx == 0) || (dirIdx2 == 1 && dirIdx == 3)) {
									result = flipTile(true, borders[tileNum2], tiles[tileNum2])
									borders[tileNum2] = result[0]
									tiles[tileNum2] = result[1]
								}
								tileNum1 = tileNum2
								done = true
							}
							else {
								List result = rotateTile(1, borders[tileNum2], tiles[tileNum2])
								borders[tileNum2] = result[0]
								tiles[tileNum2] = result[1]
								result = flipTile(true, borders[tileNum2], tiles[tileNum2])
								borders[tileNum2] = result[0]
								tiles[tileNum2] = result[1]
								
								if((dirIdx2 == 1 && dirIdx == 0) || (dirIdx2 == 2 && dirIdx == 3)) {
									result = flipTile(false, borders[tileNum2], tiles[tileNum2])
									borders[tileNum2] = result[0]
									tiles[tileNum2] = result[1]
								}
								
								tileNum1 = tileNum2
								done = true
								
							}
						}
						else if(borders[tileNum1][dirIdx].equals(border2.reverse())) {
							if(dirIdx + dirIdx2 == 3) {
								List result = flipTile(dirIdx == 0 || dirIdx == 3, borders[tileNum2], tiles[tileNum2])
								borders[tileNum2] = result[0]
								tiles[tileNum2] = result[1]
								tileNum1 = tileNum2
								done = true
							} 
							else if(dirIdx == dirIdx2) {
								List result = rotateTile(2, borders[tileNum2], tiles[tileNum2])
								borders[tileNum2] = result[0]
								tiles[tileNum2] = result[1]
								tileNum1 = tileNum2
								done = true
							}
							else if((dirIdx2 == 3 && dirIdx == 2) || (dirIdx2 == 2 && dirIdx == 0) || (dirIdx2 == 1 && dirIdx == 3) || (dirIdx2 == 0 && dirIdx == 1)) {
								List result = rotateTile(1, borders[tileNum2], tiles[tileNum2])
								borders[tileNum2] = result[0]
								tiles[tileNum2] = result[1]
								
								if((dirIdx2 == 3 && dirIdx == 2) || (dirIdx2 == 0 && dirIdx == 1)) {
									result = flipTile(false, borders[tileNum2], tiles[tileNum2])
									borders[tileNum2] = result[0]
									tiles[tileNum2] = result[1]
								}
								tileNum1 = tileNum2
								done = true
							}
							else {
								List result = rotateTile(1, borders[tileNum2], tiles[tileNum2])
								borders[tileNum2] = result[0]
								tiles[tileNum2] = result[1]
								result = flipTile(false, borders[tileNum2], tiles[tileNum2])
								borders[tileNum2] = result[0]
								tiles[tileNum2] = result[1]
								
								if((dirIdx2 == 3 && dirIdx == 1) || (dirIdx2 == 0 && dirIdx == 2)) {
									result = flipTile(true, borders[tileNum2], tiles[tileNum2])
									borders[tileNum2] = result[0]
									tiles[tileNum2] = result[1]
								}
								tileNum1 = tileNum2
								done = true
							}
						}
					}
				}
			}
			if(dirIdx == 3) {
				rowIndex++
				if(rowIndex == bigPicHeight-1) dirIdx = 2
			}
			else if(dirIdx == 2) {
				if(rowIndex == 0) dirIdx = 3
				else {
					 dirIdx = 0
				}
			}
			else if(dirIdx == 0) {
				rowIndex--
				if(rowIndex == 0) dirIdx = 2
			}
			tiles[tileNum1].eachWithIndex { String tileLine, int idx ->
				results[rowIndex*(tiles[tileNum1].size())+idx] += tileLine
			}
		}
		int numberOfMonsters = 0
		String monsterRegexMid = /#(.{4}#{2}){3}#/
		String monsterRegexBottom = /.#(.{2}#){5}.{3}/	
		
		for(int h = 0; h < 1; h++) {
			for(int v = 0; v < 2; v++) {
				for(int r = 0; r < 1; r++) {
					if(h) {
						List result = flipTile(true, ["", "", "", ""], results)
						results = result[1]
					}
					if(v) {
						List result = flipTile(false, ["", "", "", ""], results)
						results = result[1]
					}
					if(r) {
						List result = rotateTile(r, ["", "", "", ""], results)
						results = result[1]
					}
					
					for(int index = 1; index < results.size()-1; index++) {
						for(int charIndex = 0; charIndex < results[index].size() - 19; charIndex++) {
							if(results[index].getAt(charIndex) == "#") {
								if(results[index+1].substring(charIndex, charIndex+20) ==~ monsterRegexBottom) {
									if(results[index].substring(charIndex, charIndex+20) ==~ monsterRegexMid) {
										if(results[index-1].substring(charIndex, charIndex+20).getAt(18) == "#") {
											numberOfMonsters++
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		results.join().count("#") - numberOfMonsters * 15
	}
	
	
	List rotateTile(int n, List borders, List tile) {
		n.times { 
			List newTile = []
			borders = [borders[1].reverse(), borders[3], borders[0], borders[2].reverse()]
			for(int i = 0; i < tile.size(); i++) {
				newTile << tile.collect{String tileLine -> tileLine.getAt(i)}.join("").reverse()
			}
			tile = newTile
		}
		
		return [borders, tile]
	}
	
	
	List flipTile(boolean horizontal, List borders, List tile) {
		if(!horizontal) {
			List newTile = []
			for(int i = 0; i < tile.size(); i++) {
				newTile << tile[tile.size() - i - 1]
			}
			tile = newTile
			borders = [borders[3], borders[1].reverse(), borders[2].reverse(), borders[0]]
		}
		else {
			tile = tile.collect { String tileLine ->
				tileLine.reverse()
			}
			borders = [borders[0].reverse(), borders[2], borders[1], borders[3].reverse()]
		}
		
		return [borders, tile]
	}
	
	
}
