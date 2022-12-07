package com.epakesko.advent.of.code.aoc_2019.Day13

import com.epakesko.advent.of.code.aoc_2019.util.IntCode
import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day
import java.awt.Point

class Day13 extends Day {
	
	@Override
	public Object calculateResult(Object fileName) {	
		IntCode intCode = new IntCode(fileName)
		List outputValues = intCode.run().split(", ").collect{it as Integer}
		int x, y
		Map<Point, TileType> tiles = new HashMap<>();
		outputValues.withIndex().collect { element, index ->
			if(index%3 == 0) x = element
			else if(index%3 == 1) y = element
			else tiles.put(new Point(x, y), TileType.getByNumber(element))
		}
		tiles.values().count { it == TileType.BLOCK }
	}
	
	enum TileType {
		EMPTY(" "),
		BLOCK("#"),
		WALL("|"),
		PADDLE("_"),
		BALL("O");
		
		String representation
		
		TileType(String rep) {
			this.representation = rep
		}
		
		static TileType getByNumber(int code) {
			switch(code) {
				case 1: return WALL;
				case 2: return BLOCK;
				case 3: return PADDLE;
				case 4: return BALL;
				default: return EMPTY;
			}
		}
	}
	
	@Override
	public Object calculateResult2(Object fileName) {
		IntCode intCode = new IntCode(fileName, -1)
		intCode.memory[0] = 2
		List outputValues = intCode.run().split(", ").collect{it as Integer}
		int x, y
		Map<Point, TileType> tiles = new HashMap<>();
		outputValues.withIndex().collect { element, index ->
			if(index%3 == 0) x = element
			else if(index%3 == 1) y = element
			else if(x == -1 && y == 0) println "score: $element"
			else tiles.put(new Point(x, y), TileType.getByNumber(element))
		}
		for(int i = 0; i < 20; i++) {
			for(int j = 0; j < 37; j++) {
				print tiles.get(new Point(j, i)).representation
			}
			println ""
		}
	}

	
}
