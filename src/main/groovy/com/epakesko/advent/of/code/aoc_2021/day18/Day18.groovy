package com.epakesko.advent.of.code.aoc_2021.day18;

import com.epakesko.advent.of.code.aoc_2019.Day18.Day18.Node
import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day
import java.awt.Point

public class Day18 extends Day{	
	@Override
	def calculateResult(fileName){
		List<String> lines = Util.readFile(fileName)
		List<Number> numbers = new ArrayList<>()
		
		lines.eachWithIndex { line, idx ->
			//First increase the previous numbers depth
			numbers.each { num -> num.incDepth() }
			
			//Make the addition
			int depth = 0
			line.each { String c ->
				if(c.matches(/[\[]/)) {
					depth++
				}
				else if(c.matches(/[\]]/)) {
					depth--
				}
				else if(c.matches(/\d/)) {
					numbers.add(new Number(c as Integer, idx == 0 ? depth : depth+1))
				}
			}
			
			//Explode and split items
			while(true) {
				int idxToExplode = numbers.findIndexOf { 
					it.depth > 4
				}
				if(idxToExplode != -1) {
					int exploadedNumbersDepth = numbers[idxToExplode].depth
					
					if(idxToExplode > 0) {
						numbers[idxToExplode - 1].inc(numbers[idxToExplode].value)
					}
					if(idxToExplode < numbers.size() - 2) {
						numbers[idxToExplode + 2].inc(numbers[idxToExplode + 1].value)
					}
					
					numbers.removeAt(idxToExplode)
					numbers.removeAt(idxToExplode)
					numbers.add(idxToExplode, new Number(0, exploadedNumbersDepth - 1))
				}
				else {
					int idxToSplit = numbers.findIndexOf {
						it.value > 9
					}
					if(idxToSplit != -1) {
						int splittedNumbersDepth = numbers[idxToSplit].depth
						int splittedNumbersValue = numbers[idxToSplit].value
						
						numbers.removeAt(idxToSplit)
						numbers.add(idxToSplit, new Number((splittedNumbersValue+1).intdiv(2), splittedNumbersDepth+1))
						numbers.add(idxToSplit, new Number((splittedNumbersValue).intdiv(2), splittedNumbersDepth+1))
					}
					else {
						break
					}
				}
			}
		}
		
		//Assumptions have been made...
		81 * numbers[0].value +
		54 * (numbers[1].value + numbers[2].value + numbers[4].value + numbers[8].value) +
		36 * (numbers[3].value + numbers[5].value + numbers[6].value + numbers[9].value + numbers[10].value + numbers[12].value) +
		24 * (numbers[7].value + numbers[11].value + numbers[13].value + numbers[14].value) + 
		16 * numbers[15].value
	}
	
	class Number {
		int value
		int depth
		
		Number(v, d) {
			this.value = v
			this.depth = d
		}
		
		void inc(int v) {
			this.value = this.value + v
		}
		
		void incDepth() {
			this.depth++
		}
		
		@Override
		String toString() {
			"$value ($depth)"
		}
	}
	
	@Override
	def calculateResult2(fileName){
		List<String> lines = Util.readFile(fileName)
		
		int max = 0
		
		for(int i = 0; i < lines.size(); i++) {
			for(int j = 0; j < lines.size(); j++) {
				if(i == j) continue
				
				
				List<Number> numbers = new ArrayList<>()
				
				[lines[i], lines[j]].eachWithIndex { line, idx ->
					//First increase the previous numbers depth
					numbers.each { num -> num.incDepth() }
					
					//Make the addition
					int depth = 0
					line.each { String c ->
						if(c.matches(/[\[]/)) {
							depth++
						}
						else if(c.matches(/[\]]/)) {
							depth--
						}
						else if(c.matches(/\d/)) {
							numbers.add(new Number(c as Integer, idx == 0 ? depth : depth+1))
						}
					}
					
					//Explode and split items
					while(true) {
						int idxToExplode = numbers.findIndexOf {
							it.depth > 4
						}
						if(idxToExplode != -1) {
							int exploadedNumbersDepth = numbers[idxToExplode].depth
							
							if(idxToExplode > 0) {
								numbers[idxToExplode - 1].inc(numbers[idxToExplode].value)
							}
							if(idxToExplode < numbers.size() - 2) {
								numbers[idxToExplode + 2].inc(numbers[idxToExplode + 1].value)
							}
							
							numbers.removeAt(idxToExplode)
							numbers.removeAt(idxToExplode)
							numbers.add(idxToExplode, new Number(0, exploadedNumbersDepth - 1))
						}
						else {
							int idxToSplit = numbers.findIndexOf {
								it.value > 9
							}
							if(idxToSplit != -1) {
								int splittedNumbersDepth = numbers[idxToSplit].depth
								int splittedNumbersValue = numbers[idxToSplit].value
								
								numbers.removeAt(idxToSplit)
								numbers.add(idxToSplit, new Number((splittedNumbersValue+1).intdiv(2), splittedNumbersDepth+1))
								numbers.add(idxToSplit, new Number((splittedNumbersValue).intdiv(2), splittedNumbersDepth+1))
							}
							else {
								break
							}
						}
					}
				}
				
				if(numbers.size() != 16) continue
				
				int value = 81 * numbers[0].value +
				54 * (numbers[1].value + numbers[2].value + numbers[4].value + numbers[8].value) +
				36 * (numbers[3].value + numbers[5].value + numbers[6].value + numbers[9].value + numbers[10].value + numbers[12].value) +
				24 * (numbers[7].value + numbers[11].value + numbers[13].value + numbers[14].value) +
				16 * numbers[15].value
				
				if(value > max) max = value	
			}
		}
		max
	}
}
