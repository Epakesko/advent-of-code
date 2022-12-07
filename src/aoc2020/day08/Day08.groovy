package aoc2020.day08

import aoc2020.Day
import aoc2020.common.Util

class Day08 extends Day {

	@Override
	public Object calculateResult(Object fileName) {
		def lines = Util.readFile(fileName)
		
		int acc = 0
		int index = 0
		Set visitedLines = []
		
		while(!visitedLines.contains(index)) {
			visitedLines << index
			String[] splittedLine = lines[index].split(" ")
			String command = splittedLine[0]
			int param = splittedLine[1] as Integer
			println command + " " + index
			if(command == "nop") {
				index++
			}
			else if(command == "jmp") {
				index += param
			}
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
			for(int i = changedIndex + 1; i < lines.size(); i++) {
				String[] splittedLine = lines[i].split(" ")
				String command = splittedLine[0]
				int param = splittedLine[1] as Integer
				if(command == "nop") {
					lines[i] = "jmp " + param
					changedIndex = i
					break
				}
				else if(command == "jmp") {
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
				//println " " + index +  " " + command 
				if(command == "nop") {
					index++
				}
				else if(command == "jmp") {
					index += param
				}
				else if(command == "acc") {
					acc += param
					index++
				}
			}
			if(index == lines.size()) {
				return acc
			}
			else {
				String[] splittedLine = lines[changedIndex].split(" ")
				String command = splittedLine[0]
				int param = splittedLine[1] as Integer
				if(command == "nop") {
					lines[changedIndex] = "jmp" + " " + param
				}
				else if(command == "jmp") {
					lines[changedIndex] = "nop" + " " + param
				}
			}
		}
	}
}
