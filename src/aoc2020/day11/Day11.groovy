package aoc2020.day11

import aoc2020.Day
import aoc2020.common.Util

class Day11 extends Day {

	@Override
	public Object calculateResult(Object fileName) {
		List layout = Util.readFile(fileName)
		
		
		List modifiedLayout = modifyLayout(layout, 4, true)
		
		
		while(layout != modifiedLayout) {
			layout = modifiedLayout.collect()
			modifiedLayout = modifyLayout(layout, 4, true)
		}
		
		int takenSeats = 0
		modifiedLayout.each{ String row ->
			takenSeats += row.count('#')
		}
		takenSeats
	}
	
	private List modifyLayout(List<String> layout, int requiredSeats, boolean lenient) {
		List<String> newLayout = []
		
		for(int i = 0; i < layout.size(); i++) {
			String newRow = ""
			for(int j = 0; j < layout[i].length(); j++) {
				if(layout[i][j] == '.') {
					newRow = newRow.concat('.')
				}
				else if(layout[i][j] == 'L') {
					if((lenient && countAdjacentTaken(layout, i, j) == 0) || (!lenient && countAdjacentTaken2(layout, i, j) == 0)) {
						newRow = newRow.concat('#')
					}
					else newRow = newRow.concat('L')
				}
				else if(layout[i][j] == '#') {
					if((lenient && countAdjacentTaken(layout, i, j) >= requiredSeats) || (!lenient && countAdjacentTaken2(layout, i, j) >= requiredSeats)) {
						newRow = newRow.concat('L')
					}
					else newRow = newRow.concat('#')
				}
			}
			newLayout << newRow
		}
		
		return newLayout
	}
	
	private int countAdjacentTaken(List<String> layout, int i, int j) {
		int adjacentTakenSeats = 0
		if(i > 0 && layout[i-1]) {
			if(j > 0 && layout[i-1][j-1] == '#') {
				adjacentTakenSeats++
			}
			
			if(layout[i-1][j] == '#') {
				adjacentTakenSeats++
			}
			
			if(j < layout[i-1].size()-1 && layout[i-1][j+1] == '#') {
				adjacentTakenSeats++
			}
		}
		
		if(layout[i+1]) {
			if(j > 0 && layout[i+1][j-1] == '#') {
				adjacentTakenSeats++
			}
			
			if(layout[i+1][j] == '#') {
				adjacentTakenSeats++
			}
			
			if(j < layout[i-1].size()-1 && layout[i+1][j+1] == '#') {
				adjacentTakenSeats++
			}
		}
		
		if(j > 0 && layout[i][j-1] == '#') {
			adjacentTakenSeats++
		}
		
		if(j < layout[i-1].size()-1 && layout[i][j+1] == '#') {
			adjacentTakenSeats++
		}
		adjacentTakenSeats
	}
	
	private int countAdjacentTaken2(List<String> layout, int i, int j) {
		int adjacentTakenSeats = 0
		if(look(layout, i, j, -1, -1)) adjacentTakenSeats++
		if(look(layout, i, j, -1, 0)) adjacentTakenSeats++
		if(look(layout, i, j, -1, 1)) adjacentTakenSeats++
		if(look(layout, i, j, 0, -1)) adjacentTakenSeats++
		if(look(layout, i, j, 0, 1)) adjacentTakenSeats++
		if(look(layout, i, j, 1, -1)) adjacentTakenSeats++
		if(look(layout, i, j, 1, 0)) adjacentTakenSeats++
		if(look(layout, i, j, 1, 1)) adjacentTakenSeats++
		adjacentTakenSeats
	}
	
	boolean look(List<String> layout, int i, int j, int verDir, int horDir) {
		i += verDir
		j += horDir
		while(i >= 0 && i < layout.size() && j >= 0 && j < layout[i].length()) {
			if(layout[i][j] == '#') {
				return true
			}
			if(layout[i][j] == 'L') {
				return false
			}
			
			i+=verDir
			j+=horDir
		}
		return false
	}
	
	@Override
	public Object calculateResult2(Object fileName) {
		List layout = Util.readFile(fileName)
		
		List modifiedLayout = modifyLayout(layout, 5, false)
		
		while(layout != modifiedLayout) {
			layout = modifiedLayout.collect()
			modifiedLayout = modifyLayout(layout, 5, false)
		}
		
		int takenSeats = 0
		modifiedLayout.each{ String row ->
			takenSeats += row.count('#')
		}
		takenSeats
	}
}
