package com.epakesko.advent.of.code.aoc_2022.day05;

import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day

public class Day05 extends Day{	
	@Override
	def calculateResult(fileName){
		List<String> lines = Util.readFile(fileName)
		int i = 0
		String line = lines[0]
		int numOfStacks = (line.size() + 1) / 4
				
		List<List<Character>> stacks = new ArrayList<>()
		for(int j = 0; j < numOfStacks; j++) stacks.add(new ArrayList<>())

		while(line[1] != "1") {
			numOfStacks.times { idx ->
				char c = lines[i].charAt(idx * 4 + 1)
				if(c != ' ') stacks[idx].add(c)
			}
			line = lines[i++]
		}
		
		for(int j = i + 1; j < lines.size(); j++) {
			line = lines[j]
			String[] splitted = line.split()
			int count = splitted[1] as Integer
			int from = (splitted[3] as Integer) - 1
			int to = (splitted[5] as Integer) - 1
			count.times { stacks[to].push(stacks[from].pop()) }
		}
		
		stacks.collect{ it.pop() }.join("")
	}
	
	@Override
	def calculateResult2(fileName){
		List<String> lines = Util.readFile(fileName)
		int i = 0
		String line = lines[0]
		int numOfStacks = (line.size() + 1) / 4
				
		List<List<Character>> stacks = new ArrayList<>()
		for(int j = 0; j < numOfStacks; j++) stacks.add(new ArrayList<>())

		while(line[1] != "1") {
			numOfStacks.times { idx ->
				char c = lines[i].charAt(idx * 4 + 1)
				if(c != ' ') stacks[idx].add(c)
			}
			line = lines[i++]
		}
		
		for(int j = i + 1; j < lines.size(); j++) {
			line = lines[j]
			String[] splitted = line.split()
			int count = splitted[1] as Integer
			int from = (splitted[3] as Integer) - 1
			int to = (splitted[5] as Integer) - 1
			List<Character> temp = new ArrayList<>(count)
			count.times { temp.push(stacks[from].pop()) }
			count.times { stacks[to].push(temp.pop()) }
		}
		
		stacks.collect{ it.pop() }.join("")
	}
}
