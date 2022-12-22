package com.epakesko.advent.of.code.aoc_2022.day18;

import java.awt.Shape

import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day
import groovy.json.JsonSlurper

public class Day18 extends Day{
	
	
	@Override
	def calculateResult(fileName){
		List<String> lines = Util.readFile(fileName)
		Set<String> cubes = new HashSet<>()
		lines.sum { line ->
			List<Integer> positions = line.split(",").collect { it as Integer }
			cubes.add(positions)
			int neighbors = 0
			for(int i = 0; i < 3; i++) {
				positions[i]++
				if(cubes.contains(positions)) neighbors++
				positions[i] -= 2
				if(cubes.contains(positions)) neighbors++
				positions[i]++
			}
			6 - neighbors * 2
		}
	}
	
	class Face {
		int x
		int y
		int z
		DIR dir
		
		public Face(x, y, z, d) {
			this.x = x
			this.y = y
			this.z = z
			this.dir = d
		}
		
		enum DIR {
			UP,
			DOWN,
			LEFT,
			RIGHT,
			IN,
			OUT
		}
		
		String id() {
			switch(DIR) {
				case DIR.UP: return "$x,$y:${y+1},$z"
				case DIR.DOWN: return "$x,${y-1}:$y,$z"
				case DIR.LEFT: return "${x-1}:$x,$y,$z"
				case DIR.RIGHT: return "$x:${x+1},$y,$z"
				case DIR.IN: return "$x,$y,$z:${z+1}"
				case DIR.OUT: return "$x,$y,${z-1}:$z"
			}
		}
		
		List<Face> getNeighbors(Set<String> cubes) {
			List<Face> n = new ArrayList<>()
			switch(DIR) {
				case Face.DIR.UP:
					[Face.DIR.LEFT, Face.DIR.IN, Face.DIR.RIGHT, Face.DIR.OUT].each {
						String neighborCubeString = getNeighborCubeString(x, y+1, z, it)
						if(cubes.contains(neighborCubeString)) n.add(new Face(x, y+1, z, it))
					}
				case Face.DIR.DOWN:
					[Face.DIR.LEFT, Face.DIR.IN, Face.DIR.RIGHT, Face.DIR.OUT].each {
						String neighborCubeString = getNeighborCubeString(x, y-1, z, it)
						if(cubes.contains(neighborCubeString)) n.add(new Face(x, y-1, z, it))
					}
				case Face.DIR.LEFT:
					[Face.DIR.UP, Face.DIR.IN, Face.DIR.DOWN, Face.DIR.OUT].each {
						String neighborCubeString = getNeighborCubeString(x-1, y, z, it)
						if(cubes.contains(neighborCubeString)) n.add(new Face(x-1, y, z, it))
					}
				case Face.DIR.RIGHT:
					[Face.DIR.UP, Face.DIR.IN, Face.DIR.DOWN, Face.DIR.OUT].each {
						String neighborCubeString = getNeighborCubeString(x+1, y, z, it)
						if(cubes.contains(neighborCubeString)) n.add(new Face(x+1, y, z, it))
					}
				case Face.DIR.IN:
					[Face.DIR.LEFT, Face.DIR.UP, Face.DIR.RIGHT, Face.DIR.DOWN].each {
						String neighborCubeString = getNeighborCubeString(x, y, z+1, it)
						if(cubes.contains(neighborCubeString)) n.add(new Face(x, y, z+1, it))
					}
				case Face.DIR.OUT:
					[Face.DIR.LEFT, Face.DIR.UP, Face.DIR.RIGHT, Face.DIR.DOWN].each {
						String neighborCubeString = getNeighborCubeString(x, y, z-1, it)
						if(cubes.contains(neighborCubeString)) n.add(new Face(x, y, z-1, it))
					}
			}
			n
		}
	}
	
	public String getNeighborCubeString(int x, int y, int z, Face.DIR dir) {
		switch(dir) {
			case Face.DIR.UP: return "$x,${y+1},$z"
			case Face.DIR.DOWN: return "$x,${y-1},$z"
			case Face.DIR.LEFT: return "${x-1},$y,$z"
			case Face.DIR.RIGHT: return "${x+1},$y,$z"
			case Face.DIR.IN: return "$x,$y,${z+1}"
			case Face.DIR.OUT: return "$x,$y,${z-1}"
		}
	}
	
	@Override
	def calculateResult2(fileName) {
		List<String> lines = Util.readFile(fileName)
		Set<String> cubes = new HashSet<>()
		List<Integer> start = lines.min { line ->
			cubes.add(line)
			line.split(",").collect { it as Integer }[0]
		}.collect { it as Integer }
		Face f = new Face(start[0], start[1], start[2], Face.DIR.LEFT)
		
		Map<String, Face> visited = new HashMap<>()
		List<String> candidates = new ArrayList<>()
		
		visited.put(f.id(), f)
		candidates.add(f.id())
		
		while(!candidates.empty) {
			String candidate = candidates.pop()
			Face face = visited.get(f.id())
			List<Face> neighbors = face.getNeighbors(cubes)
			neighbors.each { neighbor ->
				if(!visited.containsKey(neighbor.id())) {
					visited.put()
				}
			}
		}
		
		
	}
}
