package aoc2020.day17

import aoc2020.Day
import aoc2020.common.Util

class Day17 extends Day {

	static final int CYCLES = 6
	
	@Override
	public Object calculateResult(Object fileName) {
		List lines = Util.readFile(fileName)
		lines = lines.collect { String line -> line.toList() }
		
		int[][][] cube = new int[1 + 2*CYCLES][lines[0].size() + 2*CYCLES][lines.size() + 2*CYCLES]
		
		lines.eachWithIndex { List line, int yIdx ->
			line.eachWithIndex { String state, int xIdx ->
				int stateNum = 0
				if(state == "#") stateNum = 1
				cube[CYCLES][yIdx + CYCLES][xIdx + CYCLES] = stateNum
			}
		}
		
		int activeStates = 0
		CYCLES.times {
			activeStates = 0
			int[][][] newCube = new int[1 + 2*CYCLES][lines[0].size() + 2*CYCLES][lines.size() + 2*CYCLES]
			for(int z = 0; z < cube.size(); z++) {
				for(int y = 0; y < cube[z].size(); y++) {
					for(int x = 0; x < cube[z][y].size(); x++) {
						int activeNeighbors = countActiveNeighbors(x, y, z, cube)
						int currentState = cube[z][y][x]
						
						if((currentState && (activeNeighbors == 2 || activeNeighbors == 3)) || (!currentState && activeNeighbors == 3)) {
							 newCube[z][y][x] = 1
							 activeStates++
						}
						else newCube[z][y][x] = 0
					}
				}
			}
			cube = newCube
		}
		
		activeStates
	}
	
	int countActiveNeighbors(int x, int y, int z, int[][][] cube) {
		int activeNeighbors = 0
		for(int dz = -1; dz <= 1; dz++) {
			for(int dy = -1; dy <= 1; dy++) {
				for(int dx = -1; dx <= 1; dx++) {
					if((dx != 0 || dy != 0 || dz != 0) && isNeighborActive(x, y, z, dx, dy, dz, cube)) activeNeighbors++
				}
			}
		}
		activeNeighbors
	}
	
	int countActiveNeighbors(int x, int y, int z, int w, int[][][][] hyperCube) {
		int activeNeighbors = 0
		for(int dw = -1; dw <= 1; dw++) {
			for(int dz = -1; dz <= 1; dz++) {
				for(int dy = -1; dy <= 1; dy++) {
					for(int dx = -1; dx <= 1; dx++) {
						if((dx != 0 || dy != 0 || dz != 0 || dw != 0) && isNeighborActive(x, y, z, w, dx, dy, dz, dw, hyperCube)) activeNeighbors++
					}
				}
			}
		}
		activeNeighbors
	}
	
	boolean isNeighborActive(int x, int y, int z, int dx, int dy, int dz, int[][][] cube){
		z + dz >= 0 && z + dz < cube.size() &&
		y + dy >= 0 && y + dy < cube[0].size() &&
		x + dx >= 0 && x + dx < cube[0][0].size() &&
		cube[z+dz][y+dy][x+dx] == 1
	}
	
	boolean isNeighborActive(int x, int y, int z, int w, int dx, int dy, int dz, int dw, int[][][][] hyperCube){
		w + dw >= 0 && w + dw < hyperCube.size() &&
		z + dz >= 0 && z + dz < hyperCube[0].size() &&
		y + dy >= 0 && y + dy < hyperCube[0][0].size() && 
		x + dx >= 0 && x + dx < hyperCube[0][0][0].size() &&
		hyperCube[w+dw][z+dz][y+dy][x+dx] == 1
	}

	@Override
	public Object calculateResult2(Object fileName) {
		List lines = Util.readFile(fileName)
		
		lines = lines.collect { String line -> line.toList() }
		
		int[][][][] hyperCube = new int[1 + 2*CYCLES][1 + 2*CYCLES][lines[0].size() + 2*CYCLES][lines.size() + 2*CYCLES]
		
		lines.eachWithIndex { List line, int yIdx -> 
			line.eachWithIndex { String state, int xIdx ->
				int stateNum = 0
				if(state == "#") stateNum = 1
				hyperCube[CYCLES][CYCLES][yIdx + CYCLES][xIdx + CYCLES] = stateNum 
			}
		}
		
		int activeStates = 0
		CYCLES.times {
			activeStates = 0
			int[][][][] newHyperCube = new int[1 + 2*CYCLES][1 + 2*CYCLES][lines[0].size() + 2*CYCLES][lines.size() + 2*CYCLES]
			for(int w = 0; w < hyperCube.size(); w++) {
				for(int z = 0; z < hyperCube[w].size(); z++) {
					for(int y = 0; y < hyperCube[w][z].size(); y++) {
						for(int x = 0; x < hyperCube[w][z][y].size(); x++) {
							int activeNeighbors = countActiveNeighbors(x, y, z, w, hyperCube)
							int currentState = hyperCube[w][z][y][x]
							
							if((currentState && (activeNeighbors == 2 || activeNeighbors == 3)) || (!currentState && activeNeighbors == 3)) {
								 newHyperCube[w][z][y][x] = 1
								 activeStates++
							}
							else newHyperCube[w][z][y][x] = 0
						}
					}
				}
			}
			hyperCube = newHyperCube
		}
		
		activeStates
	}
}
