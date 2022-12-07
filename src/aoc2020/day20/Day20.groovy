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
				println "" + tileNum1 + " " + hasPair + " " + borders[tileNum1]
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
		//results[0] = borders[tileNum1][0]
		tiles[tileNum1].eachWithIndex { String tileLine, int idx ->
			results[idx] += tileLine//borders[tileNum1][1].getAt(idx+1) + tileLine + borders[tileNum1][2].getAt(idx+1)
		}
		//results[9] = borders[tileNum1][3]
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
							println "tileNum1: $tileNum1 tileNum2: $tileNum2, dir1: $dirIdx, dir2: $dirIdx2"
							if(dirIdx + dirIdx2 == 3) {
								//println "should not do anything, dir1: $dirIdx, dir2: $dirIdx2"
								//println "previous: "
								tiles[tileNum1].each {
									//println it
								}
								//println "border: ${borders[tileNum1]}"
								//println ""
								//println "next: "
								tiles[tileNum2].each {
									//println it
								}
								//println "border: ${borders[tileNum2]}"
								//println "good?: ${borders[tileNum2][3-dirIdx].equals(borders[tileNum1][dirIdx])}"
								tileNum1 = tileNum2
								done = true
							} 
							else if(dirIdx == dirIdx2) {
								//println "should flip, dir of both: $dirIdx"
								//println "previous: "
								tiles[tileNum1].each {
									//println it
								}
								//println "border: ${borders[tileNum1]}"
								//println ""
								//println "next: "
								tiles[tileNum2].each {
									//println it
								}
								//println "border: ${borders[tileNum2]}"
								//println ""
								List result = flipTile(dirIdx == 1 || dirIdx == 2, borders[tileNum2], tiles[tileNum2])
								borders[tileNum2] = result[0]
								tiles[tileNum2] = result[1]
								tileNum1 = tileNum2
								//println "flipped: "
								tiles[tileNum2].each {
									//println it
								}
								//println "border: ${borders[tileNum2]}"
								//println "good?: ${borders[tileNum2][3-dirIdx].equals(borders[tileNum1][dirIdx])}"
								done = true
							}
							else if((dirIdx2 == 3 && dirIdx == 2) || (dirIdx2 == 2 && dirIdx == 0) || (dirIdx2 == 1 && dirIdx == 3) || (dirIdx2 == 0 && dirIdx == 1)) {
								//println "should rotate by 1, flip horizontally if dir2 2, dir1 0 or dir2 1, dir1 0, dir1: $dirIdx, dir2: $dirIdx2"
								//println "previous: "
								tiles[tileNum1].each {
									//println it
								}
								//println "border: ${borders[tileNum1]}"
								//println ""
								//println "next: "
								tiles[tileNum2].each {
									//println it
								}
								//println "border: ${borders[tileNum2]}"
								//println ""
								List result = rotateTile(1, borders[tileNum2], tiles[tileNum2])
								borders[tileNum2] = result[0]
								tiles[tileNum2] = result[1]
								
								if((dirIdx2 == 2 && dirIdx == 0) || (dirIdx2 == 1 && dirIdx == 3)) {
									result = flipTile(true, borders[tileNum2], tiles[tileNum2])
									borders[tileNum2] = result[0]
									tiles[tileNum2] = result[1]
								}
								//println "rotated: "
								tiles[tileNum2].each {
									//println it
								}
								//println "border: ${borders[tileNum2]}"
								//println "good?: ${borders[tileNum2][3-dirIdx].equals(borders[tileNum1][dirIdx])}"
								tileNum1 = tileNum2
								done = true
							}
							else {
								//println "should rotate by 1, flip horizontally then flip vertically if dir2 1, dir1 0 or dir2 2, dir1 3, dir1: $dirIdx, dir2: $dirIdx2"
								//println "previous: "
								tiles[tileNum1].each {
									//println it
								}
								//println "border: ${borders[tileNum1]}"
								//println ""
								//println "next: "
								tiles[tileNum2].each {
									//println it
								}
								//println "border: ${borders[tileNum2]}"
								//println ""
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
								
								//println "rotated and flipped: "
								tiles[tileNum2].each {
									//println it
								}
								//println "border: ${borders[tileNum2]}"
								//println "good?: ${borders[tileNum2][3-dirIdx].equals(borders[tileNum1][dirIdx])}"
								tileNum1 = tileNum2
								done = true
								
							}
						}
						else if(borders[tileNum1][dirIdx].equals(border2.reverse())) {
							println "tileNum1: $tileNum1 tileNum2: $tileNum2, dir1: $dirIdx, dir2: $dirIdx2"
							if(dirIdx + dirIdx2 == 3) {
								//println "should flip horizontally if dirIdx 0 or 3, dir: $dirIdx, dir2: $dirIdx2"
								//println "previous: "
								tiles[tileNum1].each {
									//println it
								}
								//println "border: ${borders[tileNum1]}"
								//println ""
								//println "next: "
								tiles[tileNum2].each {
									//println it
								}
								//println "border: ${borders[tileNum2]}"
								//println ""
								List result = flipTile(dirIdx == 0 || dirIdx == 3, borders[tileNum2], tiles[tileNum2])
								borders[tileNum2] = result[0]
								tiles[tileNum2] = result[1]
								//println "flipped: "
								tiles[tileNum2].each {
									//println it
								}
								//println "border: ${borders[tileNum2]}"
								//println "good?: ${borders[tileNum2][3-dirIdx].equals(borders[tileNum1][dirIdx])}"
								tileNum1 = tileNum2
								done = true
							} 
							else if(dirIdx == dirIdx2) {
								//println "should rotate by 2, dir of both: $dirIdx"
								//println "previous: "
								tiles[tileNum1].each {
									//println it
								}
								//println "border: ${borders[tileNum1]}"
								//println ""
								//println "next: "
								tiles[tileNum2].each {
									//println it
								}
								//println "border: ${borders[tileNum2]}"
								//println ""
								List result = rotateTile(2, borders[tileNum2], tiles[tileNum2])
								borders[tileNum2] = result[0]
								tiles[tileNum2] = result[1]
								
								//println "rotated by 2: "
								tiles[tileNum2].each {
									//println it
								}
								//println "border: ${borders[tileNum2]}"
								//println "good?: ${borders[tileNum2][3-dirIdx].equals(borders[tileNum1][dirIdx])}"
								tileNum1 = tileNum2
								done = true
							}
							else if((dirIdx2 == 3 && dirIdx == 2) || (dirIdx2 == 2 && dirIdx == 0) || (dirIdx2 == 1 && dirIdx == 3) || (dirIdx2 == 0 && dirIdx == 1)) {
								//println "should rotate by 1, then flip vertically if dir2 3, dir1 2 or dir2 0, dir1 1, dir1: $dirIdx, dir2: $dirIdx2"
								//println "previous: "
								tiles[tileNum1].each {
									//println it
								}
								//println "border: ${borders[tileNum1]}"
								//println ""
								//println "next: "
								tiles[tileNum2].each {
									//println it
								}
								//println "border: ${borders[tileNum2]}"
								//println ""
								List result = rotateTile(1, borders[tileNum2], tiles[tileNum2])
								borders[tileNum2] = result[0]
								tiles[tileNum2] = result[1]
								
								if((dirIdx2 == 3 && dirIdx == 2) || (dirIdx2 == 0 && dirIdx == 1)) {
									result = flipTile(false, borders[tileNum2], tiles[tileNum2])
									borders[tileNum2] = result[0]
									tiles[tileNum2] = result[1]
								}
								
								//println "rotated (and flipped): "
								tiles[tileNum2].each {
									//println it
								}
								//println "border: ${borders[tileNum2]}"
								//println "good?: ${borders[tileNum2][3-dirIdx].equals(borders[tileNum1][dirIdx])}"
								tileNum1 = tileNum2
								done = true
							}
							else {
								//println "should rotate by 1, then flip vertically then flip horizontally if dir2 3, dir1 1 or dir2 0, dir1 2, dir1: $dirIdx, dir2: $dirIdx2"
								//println "previous: "
								tiles[tileNum1].each {
									//println it
								}
								//println "border: ${borders[tileNum1]}"
								//println ""
								//println "next: "
								tiles[tileNum2].each {
									//println it
								}
								//println "border: ${borders[tileNum2]}"
								//println ""
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
								
								//println "rotated (and flipped): "
								tiles[tileNum2].each {
									//println it
								}
								//println "border: ${borders[tileNum2]}"
								//println "good?: ${borders[tileNum2][3-dirIdx].equals(borders[tileNum1][dirIdx])}"
								tileNum1 = tileNum2
								done = true
							}
						}
					}
				}
			}
			println "$rowIndex ${results.last().length()} ${results.first().length()}"
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
			//results[rowIndex*(tiles[tileNum1].size()+2)] += borders[tileNum1][0]
			tiles[tileNum1].eachWithIndex { String tileLine, int idx ->
				results[rowIndex*(tiles[tileNum1].size())+idx] += tileLine//borders[tileNum1][1].getAt(idx+1) + tileLine + borders[tileNum1][2].getAt(idx+1)
			}
			//results[rowIndex*(tiles[tileNum1].size()+2)+9] += borders[tileNum1][3]
		}
		int numberOfMonsters = 0
		String monsterRegexMid = /#(.{4}#{2}){3}#/
		String monsterRegexBottom = /.#(.{2}#){5}.{3}/
		
		

		
		
		
		
		for(int h = 0; h < 1; h++) {
			for(int v = 0; v < 2; v++) {
				for(int r = 0; r < 1; r++) {
					println ""
					if(h) {
						print "flipping horizontally "
						List result = flipTile(true, ["", "", "", ""], results)
						results = result[1]
					}
					if(v) {
						print "flipping vertically "
						List result = flipTile(false, ["", "", "", ""], results)
						results = result[1]
					}
					if(r) {
						print "rotating by $r"
						List result = rotateTile(r, ["", "", "", ""], results)
						results = result[1]
					}
					println ""
					
					results.each { resultLine ->
						println resultLine
					}
					
					for(int index = 1; index < results.size()-1; index++) {
						for(int charIndex = 0; charIndex < results[index].size() - 19; charIndex++) {
							if(results[index].getAt(charIndex) == "#") {
								if(results[index+1].substring(charIndex, charIndex+20) ==~ monsterRegexBottom) {
									println "find bottom at $index $charIndex"
									if(results[index].substring(charIndex, charIndex+20) ==~ monsterRegexMid) {
										println "also found mid"
										if(results[index-1].substring(charIndex, charIndex+20).getAt(18) == "#") {
											println "found"
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
		
		println results.join().count("#")
		println numberOfMonsters
		
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
