package com.epakesko.advent.of.code.aoc_2021.day20;

import com.epakesko.advent.of.code.aoc_2019.Day18.Day18.Node
import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day

import static org.junit.jupiter.api.Assertions.assertLinesMatch

import java.awt.Point

public class Day20 extends Day{	
	@Override
	def calculateResult(fileName){
		List<String> lines = Util.readFile(fileName)
		
		int requiredRounds = 2
		
		Set<Integer> algorithmString = new HashSet<>()
		lines[0].eachWithIndex { String c, int idx -> if(c.matches(/#/)) algorithmString.add(idx) }
		
		int width = lines[2].size()
		
		List<List<String>> input = new ArrayList<>() 
		
		(requiredRounds+1).times{
			List l = new ArrayList<>()
			(width+2*(requiredRounds+1)).times { l << 0 }
			input << l
		}
		for(int i = 2; i < lines.size(); i++) {
			List l = new ArrayList<>()
			(requiredRounds+1).times { l << "0" }
			lines[i].each {String c -> l << (c.matches(/#/) ? "1" : "0")}
			(requiredRounds+1).times { l << "0" }
			input << l
		}
		(requiredRounds+1).times{
			List l = new ArrayList<>()
			(width+2*(requiredRounds+1)).times { l << 0 }
			input << l
		}
		
		
		requiredRounds.times { 
			List<List<Integer>> newInput = new ArrayList<>()
			for(int i = 0; i < input.size(); i++) {
				newInput << new ArrayList<>()
				for(int j = 0; j < input[i].size(); j++) {
					String lookupBinary = ""
					if(i == 0 || j == 0 || i == input.size() - 1 || j == input[i].size() - 1) lookupBinary = input[i][j] * 9
					else lookupBinary = "${input[i-1][j-1]}${input[i-1][j]}${input[i-1][j+1]}${input[i][j-1]}${input[i][j]}${input[i][j+1]}${input[i+1][j-1]}${input[i+1][j]}${input[i+1][j+1]}"
					
					int index = Integer.parseInt(lookupBinary, 2)
					newInput.last() << (algorithmString.contains(index) ? "1" : "0")
				}
			}
			input = newInput
		}
		
		input.sum{ row -> row.sum{ cell -> Integer.parseInt(cell) } }
	}

	@Override
	def calculateResult2(fileName){
		List<String> lines = Util.readFile(fileName)
		
		int requiredRounds = 50
		
		Set<Integer> algorithmString = new HashSet<>()
		lines[0].eachWithIndex { String c, int idx -> if(c.matches(/#/)) algorithmString.add(idx) }
		
		int width = lines[2].size()
		
		List<List<String>> input = new ArrayList<>() 
		
		(requiredRounds+1).times{
			List l = new ArrayList<>()
			(width+2*(requiredRounds+1)).times { l << 0 }
			input << l
		}
		for(int i = 2; i < lines.size(); i++) {
			List l = new ArrayList<>()
			(requiredRounds+1).times { l << "0" }
			lines[i].each {String c -> l << (c.matches(/#/) ? "1" : "0")}
			(requiredRounds+1).times { l << "0" }
			input << l
		}
		(requiredRounds+1).times{
			List l = new ArrayList<>()
			(width+2*(requiredRounds+1)).times { l << 0 }
			input << l
		}
		
		
		requiredRounds.times { 
			List<List<Integer>> newInput = new ArrayList<>()
			for(int i = 0; i < input.size(); i++) {
				newInput << new ArrayList<>()
				for(int j = 0; j < input[i].size(); j++) {
					String lookupBinary = ""
					if(i == 0 || j == 0 || i == input.size() - 1 || j == input[i].size() - 1) lookupBinary = input[i][j] * 9
					else lookupBinary = "${input[i-1][j-1]}${input[i-1][j]}${input[i-1][j+1]}${input[i][j-1]}${input[i][j]}${input[i][j+1]}${input[i+1][j-1]}${input[i+1][j]}${input[i+1][j+1]}"
					
					int index = Integer.parseInt(lookupBinary, 2)
					newInput.last() << (algorithmString.contains(index) ? "1" : "0")
				}
			}
			input = newInput
		}
		
		input.sum{ row -> row.sum{ cell -> Integer.parseInt(cell) } }
	}
}
