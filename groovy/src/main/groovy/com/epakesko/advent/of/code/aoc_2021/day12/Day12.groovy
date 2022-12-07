package com.epakesko.advent.of.code.aoc_2021.day12;

import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day
import java.awt.Point

public class Day12 extends Day{	
	@Override
	def calculateResult(fileName){
		List<String> lines = Util.readFile(fileName)
		Map<String, List<String>> neighbors = new HashMap<>()
		lines.each { line ->
			def splitted = line.split("-")
			if(!neighbors.containsKey(splitted[0])) neighbors.put(splitted[0], [])
			if(!neighbors.containsKey(splitted[1])) neighbors.put(splitted[1], [])
			neighbors.get(splitted[0]) << splitted[1]
			neighbors.get(splitted[1]) << splitted[0]
		}
		
		List<Path> paths = []
		Set<String> s = ["start"]
		paths.add(new Path(s, "start"))
		
		int sum = 0
		
		while(!paths.empty) {
			List<Path> newPaths = []
			for(Path path : paths) {
				neighbors.get(path.last).each { neighbor ->
					if(!path.didVisit(neighbor)) {
						if("end".equals(neighbor)) sum++
						else newPaths << (new Path(path.visited + neighbor, neighbor))
					}
				}
			}
			paths = newPaths
		}
		sum
	}
	

	class Path {
		Set<String> visited
		String last
		boolean didVisitSmallCaveTwice
		
		Path(Set<String> vis, String last) {
			this.visited = vis
			this.last = last
			didVisitSmallCaveTwice = false
		}
		
		Path(Set<String> vis, String last, boolean v) {
			this.visited = vis
			this.last = last
			didVisitSmallCaveTwice = v
		}
		
		boolean didVisit(String n) {
			!n.matches(/[A-Z]+/) && visited.contains(n)
		}
		
		String toString() {
			return "" + last + " " + didVisitSmallCaveTwice + " " + visited 
		}
	}

	@Override
	def calculateResult2(fileName){
		List<String> lines = Util.readFile(fileName)
		Map<String, List<String>> neighbors = new HashMap<>()
		lines.each { line ->
			def splitted = line.split("-")
			if(!neighbors.containsKey(splitted[0])) neighbors.put(splitted[0], [])
			if(!neighbors.containsKey(splitted[1])) neighbors.put(splitted[1], [])
			neighbors.get(splitted[0]) << splitted[1]
			neighbors.get(splitted[1]) << splitted[0]
		}
		
		List<Path> paths = []
		Set<String> s = ["start"]
		paths.add(new Path(s, "start"))
		
		int sum = 0
		
		while(!paths.empty) {
			List<Path> newPaths = []
			for(Path path : paths) {
				neighbors.get(path.last).each { neighbor ->
					if(!path.didVisit(neighbor)) {
						if("end".equals(neighbor)) sum++
						else newPaths << (new Path(path.visited + neighbor, neighbor, path.didVisitSmallCaveTwice))
					}
					else if(!path.didVisitSmallCaveTwice && !"start".equals(neighbor)) {
						if("end".equals(neighbor)) sum++
						else newPaths << (new Path(path.visited + neighbor, neighbor, true))
					}
				}
			}
			paths = newPaths
		}
		sum
	}
}
