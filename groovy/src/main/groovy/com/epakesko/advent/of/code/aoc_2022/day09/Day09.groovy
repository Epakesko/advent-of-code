package com.epakesko.advent.of.code.aoc_2022.day09;

import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day

public class Day09 extends Day{	
	
	@Override
	def calculateResult(fileName){
		List<String> lines = Util.readFile(fileName)
		Pos h = new Pos(0,0)
		Pos t = new Pos(0,0)
		Set<String> visited = new HashSet<>()
		lines.each { line ->
			String dir = line.split(" ")[0]
			int count = line.split(" ")[1] as Integer
			visited.add("${t.x}-${t.y}")
			count.times {
				h.move(dir)
				t.moveTowards(h)
				visited.add("${t.x}-${t.y}")
			}
		}
		visited.size()
	}
	
	@Override
	def calculateResult2(fileName){
		List<String> lines = Util.readFile(fileName)
		Pos h = new Pos(0,0)
		def tails = []
		9.times { tails << new Pos(0,0) }
		Set<String> visited = new HashSet<>()
		lines.each { line ->
			String dir = line.split(" ")[0]
			int count = line.split(" ")[1] as Integer
			visited.add("${tails[8].x}-${tails[8].y}")
			count.times {
				h.move(dir)
				tails.eachWithIndex { t, idx ->
					if(idx == 0) tails[idx].moveTowards(h)
					else tails[idx].moveTowards(tails[idx - 1])
				}
				visited.add("${tails[8].x}-${tails[8].y}")
			}
		}
		visited.size()
	}
	
	class Pos {
		int x
		int y
		Pos(x, y) {
			this.x = x
			this.y = y
		}
		
		void move(String dir) {
			switch(dir) {
				case "U": 
					y++
					break
				case "D":
					y--
					break
				case "L":
					x--
					break
				case "R":
					x++
					break
			}
		}
		
		void moveTowards(Pos p) {
			int xDiff = p.x - x
			int yDiff = p.y - y
			if(Math.abs(xDiff) + Math.abs(yDiff) < 2 || (Math.abs(xDiff) == 1 && Math.abs(yDiff) == 1)) return
			if(yDiff > 0) move("U")
			if(yDiff < 0) move("D")
			if(xDiff > 0) move("R")
			if(xDiff < 0) move("L")
		} 
	}
}
