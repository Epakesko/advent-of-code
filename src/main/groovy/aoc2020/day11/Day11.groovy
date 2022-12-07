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
				int 
				if(layout[i][j] == '.') {
					newRow = newRow.concat('.')
				}
				else if(layout[i][j] == 'L') {
					if(countAdjacentTaken(layout, i, j, lenient) == 0) newRow = newRow.concat('#')
					else newRow = newRow.concat('L')
				}
				else if(layout[i][j] == '#') {
					if(countAdjacentTaken(layout, i, j, lenient) >= requiredSeats) newRow = newRow.concat('L')
					else newRow = newRow.concat('#')
				}
			}
			newLayout << newRow
		}
		
		return newLayout
	}
	
	private int countAdjacentTaken(List<String> layout, int i, int j, boolean lenient) {
		int adjacentTakenSeats = 0
		if(look(layout, i, j, -1, -1, lenient)) adjacentTakenSeats++
		if(look(layout, i, j, -1, 0, lenient)) adjacentTakenSeats++
		if(look(layout, i, j, -1, 1, lenient)) adjacentTakenSeats++
		if(look(layout, i, j, 0, -1, lenient)) adjacentTakenSeats++
		if(look(layout, i, j, 0, 1, lenient)) adjacentTakenSeats++
		if(look(layout, i, j, 1, -1, lenient)) adjacentTakenSeats++
		if(look(layout, i, j, 1, 0, lenient)) adjacentTakenSeats++
		if(look(layout, i, j, 1, 1, lenient)) adjacentTakenSeats++
		adjacentTakenSeats
	}
	
	boolean look(List<String> layout, int i, int j, int verDir, int horDir, boolean lenient) {
		i += verDir
		j += horDir
		while(i >= 0 && i < layout.size() && j >= 0 && j < layout[i].length()) {
			if(layout[i][j] == '#') {
				return true
			}
			else if(lenient) return false
			
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
