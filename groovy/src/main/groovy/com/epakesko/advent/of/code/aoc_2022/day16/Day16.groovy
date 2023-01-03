package com.epakesko.advent.of.code.aoc_2022.day16;

import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day

public class Day16 extends Day{
	
	class Valve {
		String name
		int flowRate
		Set<Valve> leadsTo
		
		public Valve(name, flowRate) {
			this(name)
			this.flowRate = flowRate
		}
		
		public Valve(name) {
			this.name = name
			leadsTo = new HashSet<>()
		}
		
		void addPath(Valve v) {
			leadsTo.add(v)
		}
		
		boolean equals(Valve v) {
			name == v.name
		}
		
		String toString() {
			"$name: $flowRate, to: ${leadsTo.collect{it.name}}"
		}
	}
	
	class Candidate {
		Valve position
		int remainingTime
		Set<Valve> opened
		int score
		String id
		
		public id() {
			"${position.name +  '-' + opened.collect{it.name}}"
		}
		
		boolean equals(Candidate c) {
			position == c.position && opened == c.opened
		}
		
		boolean better(Candidate c) {
			score <= c.score
		}
		
		public Candidate(p, t, o, s) {
			this.position = p
			this.remainingTime = t
			this.opened = o
			this.score = s
		}
		
		String toString() {
			"$position.name ${opened.collect{it.name}} $remainingTime $score"
		}
	}
	
	@Override
	def calculateResult(fileName){
		List<String> lines = Util.readFile(fileName)
		Map<String, Valve> valves = new HashMap<>()
		def pattern =  /Valve (\w{2}) has flow rate=(\d+); tunnel[s]? lead[s]? to valve[s]? (.*)/
		lines.each { line ->
			def m = line =~ pattern
			String name = m[0][1]
			int flowRate = m[0][2] as Integer
			Valve v
			if(!valves.containsKey(name)) {
				v = new Valve(name, flowRate)
				valves.put(name, v)
			}
			else {
				v = valves.get(name)
				v.flowRate = flowRate
			}
				
			m[0][3].split(", ").each { 
				Valve to
				if(!valves.containsKey(it)) {
					to = new Valve(it) 
					valves.put(it, to)
				}
				else to = valves.get(it)
				v.addPath(to)
			}
		}
		
		List<Candidate> candidates = new ArrayList<>()
		Map<String, List<Candidate>> visited = new HashMap<>()
		Candidate firstCandidate = new Candidate(valves.get("AA"), 30, new TreeSet<>({v1, v2 -> v1.name <=> v2.name}), 0)
		candidates.add(firstCandidate)
		visited.put(firstCandidate.id(), [firstCandidate])
		
		int max = 0
		
		30.times { it ->
			//println "visited: ${visited.keySet()}"
			//println "candidates: $candidates"
			println "$it ${candidates.size()}"
			List<Candidate> newCandidates = new ArrayList<>()
			while(!candidates.empty) {
				Candidate candidate = candidates.pop()
				Set<Valve> neighbors = candidate.position.leadsTo
				neighbors.each { nextValve ->
					Candidate c = new Candidate(nextValve, 30 - it - 1, candidate.opened, candidate.score)
					List<Candidate> prevC = visited.get(c.id())
					if(!prevC || !prevC.any { c.better(it) }) {
						visited.put(c.id(), [c])
						newCandidates.add(c)
					}
				}
				if(candidate.position.flowRate != 0 && !candidate.opened.contains(candidate.position)) {
					Candidate c = new Candidate(candidate.position, 30 - it - 1, candidate.opened + candidate.position, candidate.score + (30 - it - 1) * candidate.position.flowRate)
					List<Candidate> prevC = visited.get(c.id())
					if(!prevC || !prevC.any { c.better(it) }) {
						visited.put(c.id(), [c])
						newCandidates.add(c)
						if(c.score > max) max = c.score
					}
				}
			}
			candidates = newCandidates
		}
		max
	}
	
	
	
	@Override
	def calculateResult2(fileName) {
		List<String> lines = Util.readFile(fileName)
		Map<String, Valve> valves = new HashMap<>()
		def pattern =  /Valve (\w{2}) has flow rate=(\d+); tunnel[s]? lead[s]? to valve[s]? (.*)/
		lines.each { line ->
			def m = line =~ pattern
			String name = m[0][1]
			int flowRate = m[0][2] as Integer
			Valve v
			if(!valves.containsKey(name)) {
				v = new Valve(name, flowRate)
				valves.put(name, v)
			}
			else {
				v = valves.get(name)
				v.flowRate = flowRate
			}
				
			m[0][3].split(", ").each { 
				Valve to
				if(!valves.containsKey(it)) {
					to = new Valve(it) 
					valves.put(it, to)
				}
				else to = valves.get(it)
				v.addPath(to)
			}
		}
		
		List<Candidate> candidates = new ArrayList<>()
		Map<String, List<Candidate>> visited = new HashMap<>()
		Candidate firstCandidate = new Candidate(valves.get("AA"), 26, new TreeSet<>({v1, v2 -> v1.name <=> v2.name}), 0)
		candidates.add(firstCandidate)
		visited.put(firstCandidate.id(), [firstCandidate])
		Map<String, Candidate> maxPossibility = new HashMap<>()
		
		26.times { it ->
			List<Candidate> newCandidates = new ArrayList<>()
			while(!candidates.empty) {
				Candidate candidate = candidates.pop()
				Set<Valve> neighbors = candidate.position.leadsTo
				neighbors.each { nextValve ->
					Candidate c = new Candidate(nextValve, 26 - it - 1, candidate.opened, candidate.score)
					List<Candidate> prevC = visited.get(c.id())
					if(!prevC || !prevC.any { c.better(it) }) {
						visited.put(c.id(), [c])
						newCandidates.add(c)
					}
				}
				if(candidate.position.flowRate != 0 && !candidate.opened.contains(candidate.position)) {
					Candidate c = new Candidate(candidate.position, 26 - it - 1, candidate.opened + candidate.position, candidate.score + (26 - it - 1) * candidate.position.flowRate)
					List<Candidate> prevC = visited.get(c.id())
					if(!prevC || !prevC.any { c.better(it) }) {
						visited.put(c.id(), [c])
						newCandidates.add(c)
						String openedString = c.opened.toString()
						if(!maxPossibility.containsKey(openedString) || maxPossibility.get(openedString).score < c.score) {
							maxPossibility.put(openedString, c)
						}
					}
				}
			}
			candidates = newCandidates
		}
		int max = 0
		for(int i = 0; i < maxPossibility.keySet().size() - 1; i++) {
			Candidate c1 = maxPossibility.get(maxPossibility.keySet()[i])
			for(int j = i + 1; j < maxPossibility.keySet().size(); j++) {
				Candidate c2 = maxPossibility.get(maxPossibility.keySet()[j])
				if(c1.opened.intersect(c2.opened).empty) {
					if(c1.score + c2.score > max) {
						println c1
						println c2
						max = c1.score + c2.score
					}
				}
			}
		}
		max
	}
}
