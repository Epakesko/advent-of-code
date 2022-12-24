package com.epakesko.advent.of.code.aoc_2022.day18;

import java.awt.Shape

import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day
import groovy.json.JsonSlurper

public class Day18 extends Day{
	
	
	@Override
	def calculateResult(fileName){
		return -1
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
			switch(dir) {
				case Face.DIR.UP:
					if(cubes.contains("${x-1},${y+1},${z}")) n.add(new Face(x-1, y+1, z, DIR.RIGHT))
					else if(cubes.contains("${x-1},${y},${z}")) n.add(new Face(x-1, y, z, dir))
					else n.add(new Face(x, y, z, DIR.LEFT))
						
					if(cubes.contains("${x+1},${y+1},${z}")) n.add(new Face(x+1, y+1, z, DIR.LEFT))
					else if(cubes.contains("${x+1},${y},${z}")) n.add(new Face(x+1, y, z, dir))
					else n.add(new Face(x, y, z, DIR.RIGHT))
						
					if(cubes.contains("${x},${y+1},${z+1}")) n.add(new Face(x, y+1, z+1, DIR.OUT))
					else if(cubes.contains("${x},${y},${z+1}")) n.add(new Face(x, y, z+1, dir))
					else n.add(new Face(x, y, z, DIR.IN))
						
					if(cubes.contains("${x},${y+1},${z-1}")) n.add(new Face(x, y+1, z-1, DIR.IN))
					else if(cubes.contains("${x},${y},${z-1}")) n.add(new Face(x, y, z-1, dir))
					else n.add(new Face(x, y, z, DIR.OUT))
					break
						
				case Face.DIR.DOWN:
					if(cubes.contains("${x-1},${y-1},${z}")) n.add(new Face(x-1, y-1, z, DIR.RIGHT))
					else if(cubes.contains("${x-1},${y},${z}")) n.add(new Face(x-1, y, z, dir))
					else n.add(new Face(x, y, z, DIR.LEFT))
						
					if(cubes.contains("${x+1},${y-1},${z}")) n.add(new Face(x+1, y-1, z, DIR.LEFT))
					else if(cubes.contains("${x+1},${y},${z}")) n.add(new Face(x+1, y, z, dir))
					else n.add(new Face(x, y, z, DIR.RIGHT))
						
					if(cubes.contains("${x},${y-1},${z+1}")) n.add(new Face(x, y-1, z+1, DIR.OUT))
					else if(cubes.contains("${x},${y},${z+1}")) n.add(new Face(x, y, z+1, dir))
					else n.add(new Face(x, y, z, DIR.IN))
						
					if(cubes.contains("${x},${y-1},${z-1}")) n.add(new Face(x, y-1, z-1, DIR.IN))
					else if(cubes.contains("${x},${y},${z-1}")) n.add(new Face(x, y, z-1, dir))
					else n.add(new Face(x, y, z, DIR.OUT))
					break
						
				case Face.DIR.LEFT:
					if(cubes.contains("${x-1},${y+1},${z}")) n.add(new Face(x-1, y+1, z, DIR.DOWN))
					else if(cubes.contains("${x},${y+1},${z}")) n.add(new Face(x, y+1, z, dir))
					else n.add(new Face(x, y, z, DIR.UP))
						
					if(cubes.contains("${x-1},${y-1},${z}")) n.add(new Face(x-1, y-1, z, DIR.UP))
					else if(cubes.contains("${x},${y-1},${z}")) n.add(new Face(x, y-1, z, dir))
					else n.add(new Face(x, y, z, DIR.DOWN))
						
					if(cubes.contains("${x-1},${y},${z+1}")) n.add(new Face(x-1, y, z+1, DIR.IN))
					else if(cubes.contains("${x},${y},${z+1}")) n.add(new Face(x, y, z+1, dir))
					else n.add(new Face(x, y, z, DIR.OUT))
						
					if(cubes.contains("${x-1},${y},${z-1}")) n.add(new Face(x-1, y, z-1, DIR.OUT))
					else if(cubes.contains("${x},${y},${z-1}")) n.add(new Face(x, y, z-1, dir))
					else n.add(new Face(x, y, z, DIR.IN))
					break
						
				case Face.DIR.RIGHT:
					if(cubes.contains("${x+1},${y+1},${z}")) n.add(new Face(x+1, y+1, z, DIR.DOWN))
					else if(cubes.contains("${x},${y+1},${z}")) n.add(new Face(x, y+1, z, dir))
					else n.add(new Face(x, y, z, DIR.UP))
						
					if(cubes.contains("${x+1},${y-1},${z}")) n.add(new Face(x+1, y-1, z, DIR.UP))
					else if(cubes.contains("${x},${y-1},${z}")) n.add(new Face(x, y-1, z, dir))
					else n.add(new Face(x, y, z, DIR.DOWN))
						
					if(cubes.contains("${x+1},${y},${z+1}")) n.add(new Face(x+1, y, z+1, DIR.IN))
					else if(cubes.contains("${x},${y},${z+1}")) n.add(new Face(x, y, z+1, dir))
					else n.add(new Face(x, y, z, DIR.OUT))
						
					if(cubes.contains("${x+1},${y},${z-1}")) n.add(new Face(x+1, y, z-1, DIR.OUT))
					else if(cubes.contains("${x},${y},${z-1}")) n.add(new Face(x, y, z-1, dir))
					else n.add(new Face(x, y, z, DIR.IN))
					break
						
				case Face.DIR.IN:
					if(cubes.contains("${x},${y+1},${z+1}")) n.add(new Face(x, y+1, z+1, DIR.UP))
					else if(cubes.contains("${x},${y+1},${z}")) n.add(new Face(x, y+1, z, dir))
					else n.add(new Face(x, y, z, DIR.DOWN))
						
					if(cubes.contains("${x},${y-1},${z+1}")) n.add(new Face(x, y-1, z+1, DIR.DOWN))
					else if(cubes.contains("${x},${y-1},${z}")) n.add(new Face(x, y-1, z, dir))
					else n.add(new Face(x, y, z, DIR.UP))
						
					if(cubes.contains("${x+1},${y},${z+1}")) n.add(new Face(x+1, y, z+1, DIR.LEFT))
					else if(cubes.contains("${x+1},${y},${z}")) n.add(new Face(x+1, y, z, dir))
					else n.add(new Face(x, y, z, DIR.RIGHT))
						
					if(cubes.contains("${x-1},${y},${z+1}")) n.add(new Face(x-1, y, z+1, DIR.RIGHT))
					else if(cubes.contains("${x-1},${y},${z}")) n.add(new Face(x-1, y, z, dir))
					else n.add(new Face(x, y, z, DIR.LEFT))
					break
						
				case Face.DIR.OUT:
					if(cubes.contains("${x},${y+1},${z-1}")) n.add(new Face(x, y+1, z-1, DIR.UP))
					else if(cubes.contains("${x},${y+1},${z}")) n.add(new Face(x, y+1, z, dir))
					else n.add(new Face(x, y, z, DIR.DOWN))
						
					if(cubes.contains("${x},${y-1},${z-1}")) n.add(new Face(x, y-1, z-1, DIR.DOWN))
					else if(cubes.contains("${x},${y-1},${z}")) n.add(new Face(x, y-1, z, dir))
					else n.add(new Face(x, y, z, DIR.UP))
						
					if(cubes.contains("${x+1},${y},${z-1}")) n.add(new Face(x+1, y, z-1, DIR.LEFT))
					else if(cubes.contains("${x+1},${y},${z}")) n.add(new Face(x+1, y, z, dir))
					else n.add(new Face(x, y, z, DIR.RIGHT))
						
					if(cubes.contains("${x-1},${y},${z-1}")) n.add(new Face(x-1, y, z-1, DIR.RIGHT))
					else if(cubes.contains("${x-1},${y},${z}")) n.add(new Face(x-1, y, z, dir))
					else n.add(new Face(x, y, z, DIR.LEFT))
					break
			}
			n
		}
	}
	
	@Override
	def calculateResult2(fileName) {
		List<String> lines = Util.readFile(fileName)
		Set<String> cubes = new HashSet<>()
		List<Integer> start = lines.min { line ->
			cubes.add(line)
			line.split(",").collect { it as Integer }[0]
		}.split(",")
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
					visited.put(neighbor.id(), neighbor)
					candidates.add(neighbor.id())
				}
			}
		}
		println visited

		
		visited.size()
	}
}
