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
		PADDLE("T"),
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
		IntCode intCode = new IntCode(fileName)
		intCode.memory.put(0L, 2)
		Map<Point, TileType> tiles = new HashMap<>();
		int score = 0
		
		List outputValues = intCode.runUntilInput().split(", ").collect{it as Integer}
		printGame(outputValues, tiles)
		
		int ballOnX = tiles.find { it.value == TileType.BALL }.key.x
		int paddleOnX = tiles.find { it.value == TileType.PADDLE }.key.x
		if(ballOnX < paddleOnX) intCode.addInput(-1)
		else if(ballOnX > paddleOnX) intCode.addInput(1)
		else intCode.addInput(0)
		while(true) {
			outputValues = intCode.continueUntilInput().split(", ").collect{it as Integer}
			int newScore = printGame(outputValues, tiles)
			if(newScore != -1) score = newScore
			//print "Current score: $score"
			if(intCode.memory.get(intCode.pointer) % 100 == 99) break
			ballOnX = tiles.find { it.value == TileType.BALL }.key.x
			paddleOnX = tiles.find { it.value == TileType.PADDLE }.key.x
			if(ballOnX < paddleOnX) intCode.addInput(-1)
			else if(ballOnX > paddleOnX) intCode.addInput(1)
			else intCode.addInput(0)
		}
		//println ""
		score
	}

	private int printGame(List outputValues, Map<Point, TileType> tiles) {
		int x, y
		int score = -1
		outputValues.withIndex().collect { element, index ->
			if(index%3 == 0) x = element
			else if(index%3 == 1) y = element
			else if(x == -1 && y == 0) score = element
			else tiles.put(new Point(x, y), TileType.getByNumber(element))
		}
		/*
		sleep 50
		print "\033[H\033[2J"
		System.out.flush()
		for(int i = 0; i < 20; i++) {
			for(int j = 0; j < 37; j++) {
				print tiles.get(new Point(j, i)).representation
			}
			println ""
		}
		*/
		score
	}
	
}
