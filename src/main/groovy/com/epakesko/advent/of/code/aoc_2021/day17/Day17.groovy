package com.epakesko.advent.of.code.aoc_2021.day17;

import com.epakesko.advent.of.code.aoc_2019.Day18.Day18.Node
import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day
import java.awt.Point

public class Day17 extends Day{	
	@Override
	def calculateResult(fileName){
		List<String> lines = Util.readFile(fileName)
		
		int max = Math.abs(lines[1].split(" ")[0] as Integer) - 1
		
		(max + 1) * max / 2
	}
	
	@Override
	def calculateResult2(fileName){
		List<String> lines = Util.readFile(fileName)
		
		Map<Integer, List<Integer>> stepsInsideWithY = new HashMap<>()
		
		
		int minY = lines[1].split(" ")[0] as Integer
		int maxY = Math.abs(minY) - 1
		int minX = 0
		int maxX = lines[0].split(" ")[1] as Integer
		
		int yEdgeMin = lines[1].split(" ")[0] as Integer
		int yEdgeMax = lines[1].split(" ")[1] as Integer
		int xEdgeMin = lines[0].split(" ")[0] as Integer
		int xEdgeMax = lines[0].split(" ")[1] as Integer
		
		
		while((minX + 1) * minX / 2 < xEdgeMin) minX++
		
		int res = 0
		
		for(int y = minY; y <= maxY; y++) {
			for(int x = minX; x <= maxX; x++) {
				int xPos = 0
				int yPos = 0
				int xCurr = x
				int yCurr = y
				
				while(yPos >= yEdgeMin && xPos <= xEdgeMax) {
					if(yPos <= yEdgeMax && xPos >= xEdgeMin) {
						res++
						break
					}
					
					xPos += xCurr
					yPos += yCurr
					xCurr = xCurr > 0? xCurr - 1 : 0
					yCurr--
				}
			}
		}
		res
	}
}
