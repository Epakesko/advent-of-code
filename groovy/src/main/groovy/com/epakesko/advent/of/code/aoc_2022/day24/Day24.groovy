package com.epakesko.advent.of.code.aoc_2022.day24;

import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day

public class Day24 extends Day {
	
	int endX
	int endY
	
	enum Dir {
		UP,
		RIGHT,
		DOWN,
		LEFT
	}
	
	class Blizzard {
		int x
		int y
		Dir dir
		
		public Blizzard(x, y, d) {
			this.x = x
			this.y = y
			this.dir = d
		}
		
		public void move() {
			switch(dir) {
				case Dir.UP:
					y--
					if(y == 0) y = endY
					break
				case Dir.DOWN:
					y++
					if(y > endY) y = 1
					break
				case Dir.LEFT:
					x--
					if(x < 0) x = endX
					break
				case Dir.RIGHT:
					x++
					if(x > endX) x = 0
					break
			}
		}
		
		public String toString() {
			"$x $y $dir"
		}
	}
	
	@Override
	def calculateResult(fileName){
		List<String> lines = Util.readFile(fileName)
		endX = lines[0].size() - 3
		endY = lines.size() - 2
		List<Blizzard> blizzards = new ArrayList<>()
		Map<Integer, Map<Integer, List>> blizzardMap = new HashMap<>()
		lines[1..-2].eachWithIndex { line, row ->
			blizzardMap.put(row + 1, new HashMap<>())
			line[1..-2].eachWithIndex { c, col ->
				List<Blizzard> blizzardList = new ArrayList<>()
				if(c.equals("<")) blizzardList << new Blizzard(col, row + 1, Dir.LEFT)
				else if(c.equals("^")) blizzardList << new Blizzard(col, row + 1, Dir.UP)
				else if(c.equals(">")) blizzardList << new Blizzard(col, row + 1, Dir.RIGHT)
				else if(c.equals("v")) blizzardList << new Blizzard(col, row + 1, Dir.DOWN)
				blizzards.addAll(blizzardList)
				if(!blizzardList.empty) blizzardMap.get(row + 1).put(col, blizzardList)
			}
		}
		Set<String> candidates = new HashSet<>()
		candidates.add("${0} ${0}")
		
		for(int i = 0;; i++) {
			Map<Integer, Map<Integer, List>> newBlizzardMap = new HashMap<>()
			blizzards.each { blizzard ->
				blizzard.move()
				if(!newBlizzardMap.containsKey(blizzard.y)) newBlizzardMap.put(blizzard.y, new HashMap<>())
				if(!newBlizzardMap.get(blizzard.y).containsKey(blizzard.x)) newBlizzardMap.get(blizzard.y).put(blizzard.x, new ArrayList<>())
				newBlizzardMap.get(blizzard.y).get(blizzard.x) << blizzard
			}
			blizzardMap = newBlizzardMap
			Set<String> newCandidates = new HashSet<>()
			candidates.each { candidate ->
				def splitted = candidate.split(" ")
				int candidateX = splitted[0] as Integer
				int candidateY = splitted[1] as Integer
				newCandidates.addAll(getNewStatuses(candidateX, candidateY, blizzardMap))
			}
			
			if(newCandidates.contains("$endX $endY")) return i+2
			candidates = newCandidates
		}
		-1
	}
	
	Set<String> getNewStatuses(int x, int y, Map<Integer, Map<Integer, List>> blizzardMap) {
		Set<String> newStatuses = new ArrayList<>()
		if((y != 1 || x == 0) && y != 0 && blizzardMap.get(y-1)?.get(x) == null) newStatuses << "$x ${y-1}"
		if(y < endY && blizzardMap.get(y+1)?.get(x) == null) newStatuses << "$x ${y+1}"
		if(y != 0 && y != endY + 1 && x != 0 && blizzardMap.get(y)?.get(x-1) == null) newStatuses << "${x-1} $y"
		if(y != 0 && y != endY + 1 && x != endX && blizzardMap.get(y)?.get(x+1) == null) newStatuses << "${x+1} $y"
		if(blizzardMap.get(y)?.get(x) == null) newStatuses << "$x $y"
		return newStatuses
	}
	
	@Override
	def calculateResult2(fileName) {
		List<String> lines = Util.readFile(fileName)
		endX = lines[0].size() - 3
		endY = lines.size() - 2
		List<Blizzard> blizzards = new ArrayList<>()
		Map<Integer, Map<Integer, List>> blizzardMap = new HashMap<>()
		lines[1..-2].eachWithIndex { line, row ->
			blizzardMap.put(row + 1, new HashMap<>())
			line[1..-2].eachWithIndex { c, col ->
				List<Blizzard> blizzardList = new ArrayList<>()
				if(c.equals("<")) blizzardList << new Blizzard(col, row + 1, Dir.LEFT)
				else if(c.equals("^")) blizzardList << new Blizzard(col, row + 1, Dir.UP)
				else if(c.equals(">")) blizzardList << new Blizzard(col, row + 1, Dir.RIGHT)
				else if(c.equals("v")) blizzardList << new Blizzard(col, row + 1, Dir.DOWN)
				blizzards.addAll(blizzardList)
				if(!blizzardList.empty) blizzardMap.get(row + 1).put(col, blizzardList)
			}
		}
		int sum = 0
		Set<String> candidates = new HashSet<>()
		candidates.add("${0} ${0}")
		for(int i = 0;; i++) {
			blizzardMap = moveBlizzards(blizzards)
			Set<String> newCandidates = new HashSet<>()
			candidates.each { candidate ->
				def splitted = candidate.split(" ")
				int candidateX = splitted[0] as Integer
				int candidateY = splitted[1] as Integer
				newCandidates.addAll(getNewStatuses(candidateX, candidateY, blizzardMap))
			}
			if(newCandidates.contains("$endX $endY")) {
				sum += i + 2
				blizzardMap = moveBlizzards(blizzards)
				candidates.removeAll { true }
				candidates.add("$endX ${endY+1}")
				break
			}
			candidates = newCandidates
		}
		for(int i = 0;; i++) {
			blizzardMap = moveBlizzards(blizzards)
			Set<String> newCandidates = new HashSet<>()
			candidates.each { candidate ->
				def splitted = candidate.split(" ")
				int candidateX = splitted[0] as Integer
				int candidateY = splitted[1] as Integer
				newCandidates.addAll(getNewStatuses(candidateX, candidateY, blizzardMap))
			}
			if(newCandidates.contains("${0} ${0}")) {
				sum += i + 1
				candidates.removeAll { true }
				candidates.add("${0} ${0}")
				break
			}
			candidates = newCandidates
		}
		for(int i = 0;; i++) {
			blizzardMap = moveBlizzards(blizzards)
			Set<String> newCandidates = new HashSet<>()
			candidates.each { candidate ->
				def splitted = candidate.split(" ")
				int candidateX = splitted[0] as Integer
				int candidateY = splitted[1] as Integer
				newCandidates.addAll(getNewStatuses(candidateX, candidateY, blizzardMap))
			}
			if(newCandidates.contains("$endX $endY")) return sum + i + 2
			candidates = newCandidates
		}
		-1
	}
	
	public Map<Integer, Map<Integer, List>> moveBlizzards(List<Blizzard> blizzards) {
		Map<Integer, Map<Integer, List>> newBlizzardMap = new HashMap<>()
		blizzards.each { blizzard ->
			blizzard.move()
			if(!newBlizzardMap.containsKey(blizzard.y)) newBlizzardMap.put(blizzard.y, new HashMap<>())
			if(!newBlizzardMap.get(blizzard.y).containsKey(blizzard.x)) newBlizzardMap.get(blizzard.y).put(blizzard.x, new ArrayList<>())
			newBlizzardMap.get(blizzard.y).get(blizzard.x) << blizzard
		}
		return newBlizzardMap
	}
	
}
