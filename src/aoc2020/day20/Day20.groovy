package aoc2020.day20

import aoc2020.Day
import aoc2020.common.Util

class Day20 extends Day {

	@Override
	public Object calculateResult(Object fileName) {
		List lines = Util.readFile(fileName)
		
		int i = 0
		int tileNum = -1
		
		Map possibilities = [:]
		
		for(String line = lines[i]; i < lines.size(); line = lines[++i]) {
			if(i % 12 == 0) {
				tileNum = line.split("Tile ")[1].split(":")[0] as Integer
			}
			else if(i % 12 == 11) continue
			else if(i % 12 == 1) {
				possibilities[tileNum] = []
				possibilities[tileNum] << line
				possibilities[tileNum] << line.getAt(0)
				possibilities[tileNum] << line.getAt(line.length()-1)				
			} 
			else if(i % 12 == 10) {
				possibilities[tileNum] << line
				possibilities[tileNum][1] += line.getAt(0)
				possibilities[tileNum][2] += line.getAt(line.length()-1)	
			}
			else {
				possibilities[tileNum][1] += line.getAt(0)
				possibilities[tileNum][2] += line.getAt(line.length()-1)	
			}
		}
		long product = 1
		possibilities.each { tileNum1, listOfBorders1 ->
			Set hasPair = []
			possibilities.each { tileNum2, listOfBorders2 ->
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
				println tileNum1
				product *= tileNum1
			}
		}
		
		product
	}

	@Override
	public Object calculateResult2(Object fileName) {
		// TODO Auto-generated method stub
		return null
	}
}
