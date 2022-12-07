package aoc2020.day08

import aoc2020.Day
import aoc2020.common.Util
import groovy.time.TimeCategory

class Day08 extends Day {

	@Override
	public Object calculateResult(Object fileName) {
		List lines = Util.readFile(fileName)
		
		int acc = 0
		int index = 0
		Set visitedLines = []
		
		while(!visitedLines.contains(index)) {
			visitedLines << index
			String[] splittedLine = lines[index].split(" ")
			String command = splittedLine[0]
			int param = splittedLine[1] as Integer
			
			if(command == "nop") index++
			else if(command == "jmp") index += param
			else if(command == "acc") {
				acc += param
				index++
			}
		}
		
		return acc
	}

	@Override
	public Object calculateResult2(Object fileName) {
		List lines = Util.readFile(fileName)
		int changedIndex = -1
		while(true) {
			String oldLine
			for(int i = changedIndex + 1; i < lines.size(); i++) {
				String[] splittedLine = lines[i].split(" ")
				String command = splittedLine[0]
				int param = splittedLine[1] as Integer
				if(command == "nop") {
					oldLine = lines[i]
					lines[i] = "jmp " + param
					changedIndex = i
					break
				}
				else if(command == "jmp") {
					oldLine = lines[i]
					lines[i] = "nop " + param
					changedIndex = i
					break
				}
			}
			int acc = 0
			int index = 0
			Set visitedLines = []
			
			while(!visitedLines.contains(index) && index != lines.size()) {
				visitedLines << index
				String[] splittedLine = lines[index].split(" ")
				String command = splittedLine[0]
				int param = splittedLine[1] as Integer
				
				if(command == "nop") index++
				else if(command == "jmp") index += param
				else if(command == "acc") {
					acc += param
					index++
				}
			}
			
			if(index == lines.size()) return acc
			
			lines[changedIndex] = oldLine
		}
	}
}
