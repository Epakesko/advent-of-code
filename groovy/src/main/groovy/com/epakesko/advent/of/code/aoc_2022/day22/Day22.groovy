package com.epakesko.advent.of.code.aoc_2022.day22;

import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day

public class Day22 extends Day{

	enum Tile {
		EMPTY(" "),
		OPEN("."),
		WALL("#")
		
		String c
		private Tile(String c) {
			this.c = c
		}
		
		public String toString() {
			return c
		}
	}
	
	enum Dir {
		RIGHT(0),
		LEFT(2),
		UP(3),
		DOWN(1)
		
		int v
		private Dir(int v) {
			this.v = v
		}
	}
	
	@Override
	def calculateResult(fileName){
		List<String> lines = Util.readFile(fileName)
		List<List<Tile>> rows = new ArrayList<>()
		List<List<Tile>> columns = new ArrayList<>()
		int x = -1
		int y = -1
		Dir dir = Dir.RIGHT;
		int width = lines[0..-2].max{ it.size() }.size()
		lines.collect{it.padRight(width)}.eachWithIndex { line, row ->
			if(row >= lines.size() - 2) return
			line.eachWithIndex { c, column ->
				Tile tile = Tile.EMPTY
				if(c.equals(".")) tile = Tile.OPEN
				else if(c.equals("#")) tile = Tile.WALL
				if(x == -1 && tile == Tile.OPEN) {
					y = row
					x = column
				}
				if(columns.size() <= column) {
					columns << new ArrayList<>()
				}
				if(column == 0) {
					rows << new ArrayList<>()
				}
				columns[column] << tile
				rows.last() << tile
			}
		}
		
		lines.last().eachMatch(/(\d+)(R|L)?/) { move ->
			int length = move[1] as Integer
			String turnDir = move[2]
			
			switch(dir) {
				case Dir.RIGHT:
					for(int i = 0; i < length; i++) {
						if(x == rows[y].size() - 1 || rows[y][x+1] == Tile.EMPTY) {
							int rowLeftStart = rows[y].findIndexOf { it != Tile.EMPTY }
							if(rows[y][rowLeftStart] == Tile.WALL) break
							else x = rowLeftStart
						}
						else if(rows[y][x+1] == Tile.OPEN) x++
						else break
					}
					break
				case Dir.LEFT:
					for(int i = 0; i < length; i++) {
						if(x == 0 || rows[y][x-1] == Tile.EMPTY) {
							int rowRightStart = rows[y].size() - rows[y].reverse().findIndexOf { it != Tile.EMPTY } - 1
							if(rows[y][rowRightStart] == Tile.WALL) break
							else x = rowRightStart
						}
						else if(rows[y][x-1] == Tile.OPEN) x--
						else break
					}
					break
				case Dir.UP:
					for(int i = 0; i < length; i++) {
						if(y == 0 || columns[x][y-1] == Tile.EMPTY) {
							int rowDownStart = columns[x].size() - columns[x].reverse().findIndexOf { it != Tile.EMPTY } - 1
							if(columns[x][rowDownStart] == Tile.WALL) break
							else y = rowDownStart
						}
						else if(columns[x][y-1] == Tile.OPEN) y--
						else break
					}
					break
				case Dir.DOWN:
					for(int i = 0; i < length; i++) {
						if(y == columns[x].size() - 1 || columns[x][y+1] == Tile.EMPTY) {
							int rowUpStart = columns[x].findIndexOf { it != Tile.EMPTY }
							if(columns[x][rowUpStart] == Tile.WALL) break
							else y = rowUpStart
						}
						else if(columns[x][y+1] == Tile.OPEN) y++
						else break
					}
					break
			}
			if(turnDir != null) dir = turn(dir, turnDir)
		}
		(y + 1) * 1000 + (x + 1) * 4 + dir.v 
	}
	
	Dir turn(Dir dir, String turnDir) {
		switch(dir) {
			case Dir.UP:
				return turnDir.equals("R")? Dir.RIGHT : Dir.LEFT
			case Dir.RIGHT:
				return turnDir.equals("R")? Dir.DOWN : Dir.UP
			case Dir.DOWN:
				return turnDir.equals("R")? Dir.LEFT : Dir.RIGHT
			case Dir.LEFT:
				return turnDir.equals("R")? Dir.UP : Dir.DOWN
		}
	}
	
	def getNextPosition(int x, int y, Dir dir) {
		int bigRow = y / faceHeight
		int smallRowInBigRow = y % faceHeight
		int bigCol = x / faceWidth
		int smallColInBigCol = x % faceWidth
		switch(dir) {
			case Dir.RIGHT: 
				switch(bigRow) {
					case 0:
						int newY = faceHeight * 3 - smallRowInBigRow - 1
						return [rows[newY].findLastIndexOf { it != Tile.EMPTY }, newY, Dir.LEFT]
					case 1:
						int newX = faceWidth * 2 + smallRowInBigRow
						return [newX, columns[newX].findLastIndexOf { it != Tile.EMPTY }, Dir.UP]
					case 2:
						int newY = faceHeight * 1 - smallRowInBigRow - 1
						return [rows[newY].findLastIndexOf { it != Tile.EMPTY }, newY, Dir.LEFT]
					case 3:
						int newX = faceWidth * 1 + smallRowInBigRow
						return [newX, columns[newX].findLastIndexOf { it != Tile.EMPTY }, Dir.UP]
				}
			case Dir.LEFT: 
				switch(bigRow) {
					case 0:
						int newY = faceHeight * 3 - smallRowInBigRow - 1
						return [rows[newY].findIndexOf { it != Tile.EMPTY }, newY, Dir.RIGHT]
					case 1:
						int newX = smallRowInBigRow
						return [newX, columns[newX].findIndexOf { it != Tile.EMPTY }, Dir.DOWN]
					case 2:
						int newY = faceHeight * 1 - smallRowInBigRow - 1
						return [rows[newY].findIndexOf { it != Tile.EMPTY }, newY, Dir.RIGHT]
					case 3:
						int newX = faceWidth * 1 + smallRowInBigRow
						return [newX, columns[newX].findIndexOf { it != Tile.EMPTY }, Dir.DOWN]
				}
			case Dir.DOWN: 
				switch(bigCol) {
					case 0:
						int newX = faceWidth * 2 + smallColInBigCol 
						return [newX, columns[newX].findIndexOf { it != Tile.EMPTY }, Dir.DOWN]
					case 1:
						int newY = faceHeight * 3 + smallColInBigCol
						return [rows[newY].findLastIndexOf { it != Tile.EMPTY }, newY, Dir.LEFT]
					case 2:
						int newY = faceHeight * 1 + smallColInBigCol
						return [rows[newY].findLastIndexOf { it != Tile.EMPTY }, newY, Dir.LEFT]
				}
			case Dir.UP: 
				switch(bigCol) {
					case 0:
						int newY = faceHeight * 1 + smallColInBigCol
						return [rows[newY].findIndexOf { it != Tile.EMPTY }, newY, Dir.RIGHT]
					case 1:
						int newY = faceHeight * 3 + smallColInBigCol
						return [rows[newY].findIndexOf { it != Tile.EMPTY }, newY, Dir.RIGHT]
					case 2:
						int newX = smallColInBigCol 
						return [newX, columns[newX].findLastIndexOf { it != Tile.EMPTY }, Dir.UP]
				}
		}
	}
	
	int faceHeight = 0
	int faceWidth = 0
	List<List<Tile>> rows = new ArrayList<>()
	List<List<Tile>> columns = new ArrayList<>()
	
	@Override
	def calculateResult2(fileName) {
		List<String> lines = Util.readFile(fileName)
		int x = -1
		int y = -1
		Dir dir = Dir.RIGHT
		int width = lines[0..-2].max{ it.size() }.size()
		lines.collect{it.padRight(width)}.eachWithIndex { line, row ->
			if(row >= lines.size() - 2) return
			line.eachWithIndex { c, column ->
				Tile tile = Tile.EMPTY
				if(c.equals(".")) tile = Tile.OPEN
				else if(c.equals("#")) tile = Tile.WALL
				if(x == -1 && tile == Tile.OPEN) {
					y = row
					x = column
				}
				if(columns.size() <= column) {
					columns << new ArrayList<>()
				}
				if(column == 0) {
					rows << new ArrayList<>()
				}
				columns[column] << tile
				rows.last() << tile
			}
		}
		faceHeight = lines.size() / 4
		faceWidth = width / 3
		lines.last().eachMatch(/(\d+)(R|L)?/) { move ->
			int length = move[1] as Integer
			String turnDir = move[2]
			
			println "$x $y $dir $length $turnDir"
			for(int i = 0; i < length; i++) {
				boolean hitWall = false
				switch(dir) {
					case Dir.RIGHT:
						if(x == rows[y].size() - 1 || rows[y][x+1] == Tile.EMPTY) {
							def (newX, newY, newDir) = getNextPosition(x, y, dir)
							Tile nextTile = rows[newY][newX]
							if(nextTile == Tile.WALL) hitWall = true
							else {
								x = newX
								y = newY
								dir = newDir
							}
						}
						else if(rows[y][x+1] == Tile.OPEN) x++
						else hitWall = true
						break
					case Dir.LEFT:
						if(x == 0 || rows[y][x-1] == Tile.EMPTY) {
							def (newX, newY, newDir) = getNextPosition(x, y, dir)
							Tile nextTile = rows[newY][newX]
							if(nextTile == Tile.WALL) hitWall = true
							else {
								x = newX
								y = newY
								dir = newDir
							}
						}
						else if(rows[y][x-1] == Tile.OPEN) x--
						else hitWall = true
						break
					case Dir.UP:
						if(y == 0 || columns[x][y-1] == Tile.EMPTY) {
							def (newX, newY, newDir) = getNextPosition(x, y, dir)
							Tile nextTile = rows[newY][newX]
							if(nextTile == Tile.WALL) hitWall = true
							else {
								x = newX
								y = newY
								dir = newDir
							}
						}
						else if(columns[x][y-1] == Tile.OPEN) y--
						else hitWall = true
						break
					case Dir.DOWN:
						if(y == columns[x].size() - 1 || columns[x][y+1] == Tile.EMPTY) {
							def (newX, newY, newDir) = getNextPosition(x, y, dir)
							Tile nextTile = rows[newY][newX]
							if(nextTile == Tile.WALL) hitWall = true
							else {
								x = newX
								y = newY
								dir = newDir
							}
						}
						else if(columns[x][y+1] == Tile.OPEN) y++
						else hitWall = true
						break
				}
				if(hitWall) break
			}
			if(turnDir != null) dir = turn(dir, turnDir)
		}
		(y + 1) * 1000 + (x + 1) * 4 + dir.v 
	}
}
