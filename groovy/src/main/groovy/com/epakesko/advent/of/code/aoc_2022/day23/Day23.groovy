package com.epakesko.advent.of.code.aoc_2022.day23;

import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day

public class Day23 extends Day{
	
	class Elf {
		int x
		int y
		Integer proposedX
		Integer proposedY
		
		public Elf(x, y) {
			this.x = x
			this.y = y
		}
		
		public boolean proposePosition(Map<Integer, Map<Integer, Elf>> elves, int idx) {
			if(elves[y-1]?.get(x-1) == null && elves[y-1]?.get(x) == null && elves[y-1]?.get(x+1) == null &&
				elves[y+1]?.get(x-1) == null && elves[y+1]?.get(x) == null && elves[y+1]?.get(x+1) == null &&
				elves[y]?.get(x-1) == null && elves[y]?.get(x+1) == null) return false
			int shift = 0
			while(shift != 4) {
				if((idx + shift) % 4 == 0) {
					if(!elves.containsKey(y-1) || (!elves[y-1].containsKey(x-1) && !elves[y-1].containsKey(x) && !elves[y-1].containsKey(x+1))) {
						proposedX = x
						proposedY = y-1
						return true
					}
					shift++
				}
				else if((idx + shift) % 4 == 1) {
					if(!elves.containsKey(y+1) || (!elves[y+1].containsKey(x-1) && !elves[y+1].containsKey(x) && !elves[y+1].containsKey(x+1))) {
						proposedX = x
						proposedY = y+1
						return true
					}
					shift++
				}
				else if((idx + shift) % 4 == 2) {
					if(!(elves.containsKey(y-1) && elves[y-1].containsKey(x-1) ||
						elves[y].containsKey(x-1) ||
						elves.containsKey(y+1) && elves[y+1].containsKey(x-1))) {
						proposedX = x-1
						proposedY = y
						return true
					}
					shift++
				}
				else if((idx + shift) % 4 == 3) {
					if(!(elves.containsKey(y-1) && elves[y-1].containsKey(x+1) ||
						elves[y].containsKey(x+1) ||
						elves.containsKey(y+1) && elves[y+1].containsKey(x+1))) {
						proposedX = x+1
						proposedY = y
						return true
					}
					shift++
				}
			}
			
			false
		}
		
		public boolean move(Map<Integer, Map<Integer, Elf>> newElves, Map<String, Integer> moveProposals) {
			if((proposedX == null && proposedY == null) || moveProposals["$proposedX $proposedY"] != 1) {
				proposedX = null
				proposedY = null
				if(!newElves.containsKey(y)) newElves.put(y, new HashMap<>())
				newElves[y].put(x, this)
				return false
			}
			else {
				x = proposedX
				y = proposedY
				proposedX = null
				proposedY = null
				if(!newElves.containsKey(y)) newElves.put(y, new HashMap<>())
				newElves[y].put(x, this)
				return true
			}
		}
	}
	
	@Override
	def calculateResult(fileName){
		List<String> lines = Util.readFile(fileName)
		Map<Integer, Map<Integer, Elf>> elves = new HashMap<>()
		int elfCount = 0
		lines.eachWithIndex { line, y ->
			elves.put(y, new HashMap<>())
			line.eachWithIndex { c, x ->
				if(c.equals("#")) {
					 elves[y][x] = new Elf(x, y)
					 elfCount++
				}
			}
		}
		
		10.times {
			Map<String, Integer> moveProposals = new HashMap<>()
			elves.values().each { elvesByRow ->
				elvesByRow.values().each { elf ->
					boolean moving = elf.proposePosition(elves, it)
					if(moving) {
						String proposedPos = "${elf.proposedX} ${elf.proposedY}"
						if(moveProposals.containsKey(proposedPos)) moveProposals[proposedPos]++
						else moveProposals[proposedPos] = 1
					}
				}
			}
			
			Map<Integer, Map<Integer, Elf>> newElves = new HashMap<>()
			elves.values().each { elvesByRow ->
				elvesByRow.values().each { elf ->
					elf.move(newElves, moveProposals)
				}
			}
			elves = newElves
		}
		
		int yMin = elves.keySet().min()
		int yMax = elves.keySet().max()
		int xMin = elves.values().min{it.keySet().min()}.keySet().min()
		int xMax = elves.values().max{it.keySet().max()}.keySet().max()
		
		(yMax - yMin + 1) * (xMax - xMin + 1) - elfCount
	}
	
	@Override
	def calculateResult2(fileName) {
		List<String> lines = Util.readFile(fileName)
		Map<Integer, Map<Integer, Elf>> elves = new HashMap<>()
		int elfCount = 0
		lines.eachWithIndex { line, y ->
			elves.put(y, new HashMap<>())
			line.eachWithIndex { c, x ->
				if(c.equals("#")) {
					 elves[y][x] = new Elf(x, y)
					 elfCount++
				}
			}
		}
		
		for(int i = 0;; i++) {
			Map<String, Integer> moveProposals = new HashMap<>()
			elves.values().each { elvesByRow ->
				elvesByRow.values().each { elf ->
					boolean moving = elf.proposePosition(elves, i)
					if(moving) {
						String proposedPos = "${elf.proposedX} ${elf.proposedY}"
						if(moveProposals.containsKey(proposedPos)) moveProposals[proposedPos]++
						else moveProposals[proposedPos] = 1
					}
				}
			}
			
			Map<Integer, Map<Integer, Elf>> newElves = new HashMap<>()
			boolean moved = false
			elves.values().each { elvesByRow ->
				elvesByRow.values().each { elf ->
					if(elf.move(newElves, moveProposals)) moved = true
				}
			}
			if(!moved) return i+1
			elves = newElves
		}
	}
}
