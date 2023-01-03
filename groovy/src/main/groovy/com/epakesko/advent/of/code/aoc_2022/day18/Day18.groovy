package com.epakesko.advent.of.code.aoc_2022.day18;

import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day

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
	
	
	
	enum DIR {
		UP,
		DOWN,
		LEFT,
		RIGHT,
		FRONT,
		BACK
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
		
		String id() {
			switch(dir) {
				case DIR.UP: return "$x,$y:${y+1},$z"
				case DIR.DOWN: return "$x,${y-1}:$y,$z"
				case DIR.LEFT: return "${x-1}:$x,$y,$z"
				case DIR.RIGHT: return "$x:${x+1},$y,$z"
				case DIR.BACK: return "$x,$y,$z:${z+1}"
				case DIR.FRONT: return "$x,$y,${z-1}:$z"
			}
		}
		
		List<Face> getNeighbors(Set<String> cubes) {
			List<Face> n = new ArrayList<>()
			switch(dir) {
				case DIR.UP:
					if(cubes.contains("${x-1},${y+1},${z}")) n.add(new Face(x-1, y+1, z, DIR.RIGHT))
					else if(cubes.contains("${x-1},${y},${z}")) n.add(new Face(x-1, y, z, dir))
					else n.add(new Face(x, y, z, DIR.LEFT))
						
					if(cubes.contains("${x+1},${y+1},${z}")) n.add(new Face(x+1, y+1, z, DIR.LEFT))
					else if(cubes.contains("${x+1},${y},${z}")) n.add(new Face(x+1, y, z, dir))
					else n.add(new Face(x, y, z, DIR.RIGHT))
						
					if(cubes.contains("${x},${y+1},${z+1}")) n.add(new Face(x, y+1, z+1, DIR.FRONT))
					else if(cubes.contains("${x},${y},${z+1}")) n.add(new Face(x, y, z+1, dir))
					else n.add(new Face(x, y, z, DIR.BACK))
						
					if(cubes.contains("${x},${y+1},${z-1}")) n.add(new Face(x, y+1, z-1, DIR.BACK))
					else if(cubes.contains("${x},${y},${z-1}")) n.add(new Face(x, y, z-1, dir))
					else n.add(new Face(x, y, z, DIR.FRONT))
					break
						
				case DIR.DOWN:
					if(cubes.contains("${x-1},${y-1},${z}")) n.add(new Face(x-1, y-1, z, DIR.RIGHT))
					else if(cubes.contains("${x-1},${y},${z}")) n.add(new Face(x-1, y, z, dir))
					else n.add(new Face(x, y, z, DIR.LEFT))
						
					if(cubes.contains("${x+1},${y-1},${z}")) n.add(new Face(x+1, y-1, z, DIR.LEFT))
					else if(cubes.contains("${x+1},${y},${z}")) n.add(new Face(x+1, y, z, dir))
					else n.add(new Face(x, y, z, DIR.RIGHT))
						
					if(cubes.contains("${x},${y-1},${z+1}")) n.add(new Face(x, y-1, z+1, DIR.FRONT))
					else if(cubes.contains("${x},${y},${z+1}")) n.add(new Face(x, y, z+1, dir))
					else n.add(new Face(x, y, z, DIR.BACK))
						
					if(cubes.contains("${x},${y-1},${z-1}")) n.add(new Face(x, y-1, z-1, DIR.BACK))
					else if(cubes.contains("${x},${y},${z-1}")) n.add(new Face(x, y, z-1, dir))
					else n.add(new Face(x, y, z, DIR.FRONT))
					break
						
				case DIR.LEFT:
					if(cubes.contains("${x-1},${y+1},${z}")) n.add(new Face(x-1, y+1, z, DIR.DOWN))
					else if(cubes.contains("${x},${y+1},${z}")) n.add(new Face(x, y+1, z, dir))
					else n.add(new Face(x, y, z, DIR.UP))
						
					if(cubes.contains("${x-1},${y-1},${z}")) n.add(new Face(x-1, y-1, z, DIR.UP))
					else if(cubes.contains("${x},${y-1},${z}")) n.add(new Face(x, y-1, z, dir))
					else n.add(new Face(x, y, z, DIR.DOWN))
						
					if(cubes.contains("${x-1},${y},${z+1}")) n.add(new Face(x-1, y, z+1, DIR.FRONT))
					else if(cubes.contains("${x},${y},${z+1}")) n.add(new Face(x, y, z+1, dir))
					else n.add(new Face(x, y, z, DIR.BACK))
						
					if(cubes.contains("${x-1},${y},${z-1}")) n.add(new Face(x-1, y, z-1, DIR.BACK))
					else if(cubes.contains("${x},${y},${z-1}")) n.add(new Face(x, y, z-1, dir))
					else n.add(new Face(x, y, z, DIR.FRONT))
					break
						
				case DIR.RIGHT:
					if(cubes.contains("${x+1},${y+1},${z}")) n.add(new Face(x+1, y+1, z, DIR.DOWN))
					else if(cubes.contains("${x},${y+1},${z}")) n.add(new Face(x, y+1, z, dir))
					else n.add(new Face(x, y, z, DIR.UP))
						
					if(cubes.contains("${x+1},${y-1},${z}")) n.add(new Face(x+1, y-1, z, DIR.UP))
					else if(cubes.contains("${x},${y-1},${z}")) n.add(new Face(x, y-1, z, dir))
					else n.add(new Face(x, y, z, DIR.DOWN))
						
					if(cubes.contains("${x+1},${y},${z+1}")) n.add(new Face(x+1, y, z+1, DIR.FRONT))
					else if(cubes.contains("${x},${y},${z+1}")) n.add(new Face(x, y, z+1, dir))
					else n.add(new Face(x, y, z, DIR.BACK))
						
					if(cubes.contains("${x+1},${y},${z-1}")) n.add(new Face(x+1, y, z-1, DIR.BACK))
					else if(cubes.contains("${x},${y},${z-1}")) n.add(new Face(x, y, z-1, dir))
					else n.add(new Face(x, y, z, DIR.FRONT))
					break
						
				case DIR.BACK:
					if(cubes.contains("${x},${y+1},${z+1}")) n.add(new Face(x, y+1, z+1, DIR.DOWN))
					else if(cubes.contains("${x},${y+1},${z}")) n.add(new Face(x, y+1, z, dir))
					else n.add(new Face(x, y, z, DIR.UP))
						
					if(cubes.contains("${x},${y-1},${z+1}")) n.add(new Face(x, y-1, z+1, DIR.UP))
					else if(cubes.contains("${x},${y-1},${z}")) n.add(new Face(x, y-1, z, dir))
					else n.add(new Face(x, y, z, DIR.DOWN))
						
					if(cubes.contains("${x+1},${y},${z+1}")) n.add(new Face(x+1, y, z+1, DIR.LEFT))
					else if(cubes.contains("${x+1},${y},${z}")) n.add(new Face(x+1, y, z, dir))
					else n.add(new Face(x, y, z, DIR.RIGHT))
						
					if(cubes.contains("${x-1},${y},${z+1}")) n.add(new Face(x-1, y, z+1, DIR.RIGHT))
					else if(cubes.contains("${x-1},${y},${z}")) n.add(new Face(x-1, y, z, dir))
					else n.add(new Face(x, y, z, DIR.LEFT))
					break
						
				case DIR.FRONT:
					if(cubes.contains("${x},${y+1},${z-1}")) n.add(new Face(x, y+1, z-1, DIR.DOWN))
					else if(cubes.contains("${x},${y+1},${z}")) n.add(new Face(x, y+1, z, dir))
					else n.add(new Face(x, y, z, DIR.UP))
						
					if(cubes.contains("${x},${y-1},${z-1}")) n.add(new Face(x, y-1, z-1, DIR.UP))
					else if(cubes.contains("${x},${y-1},${z}")) n.add(new Face(x, y-1, z, dir))
					else n.add(new Face(x, y, z, DIR.DOWN))
						
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
		Set<CharSequence> cubes = new HashSet<>()
		List<Integer> start = lines.min { line ->
			cubes.add("${line}")
			line.split(",").collect { it as Integer }[0]
		}.split(",").collect { it as Integer }
		Face f = new Face(start[0], start[1], start[2], DIR.LEFT)
		
		Map<String, Face> visited = new HashMap<>()
		List<String> candidates = new ArrayList<>()
		
		visited.put(f.id(), f)
		candidates.add(f.id())
		println cubes
		int x = 3
		int y = 2
		int z = 1
		println cubes.contains("2,2,2")
		println cubes.contains("${x-1},${y},${z+1}".toString())
		println cubes.contains("${x-1},${y},${z+1}")
		while(!candidates.empty) {
			String candidate = candidates.pop()
			Face face = visited.get(candidate)
			println "${face.x} ${face.y} ${face.z} ${face.dir}"
			List<Face> neighbors = face.getNeighbors(cubes)
			neighbors.each { neighbor ->
				println "    ${neighbor.x} ${neighbor.y} ${neighbor.z} ${neighbor.dir}"
				if(!visited.containsKey(neighbor.id())) {
					visited.put(neighbor.id(), neighbor)
					candidates.add(neighbor.id())
				}
			}
			println candidates
		}

		println visited.keySet()
		
		visited.size()
	}
}
