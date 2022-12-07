package com.epakesko.advent.of.code.aoc_2021.day08;

import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day

public class Day08 extends Day{	
	@Override
	def calculateResult(fileName){
		List<String> lines = Util.readFile(fileName)
		int sum
		lines.each { line ->
			def outputDigits = line.split(/[|]/)[1]
			outputDigits.split(" ").each { digit ->
				if(digit.size() == 2 || digit.size() == 3 || digit.size() == 4 || digit.size() == 7) sum++
			}
		}
		sum
	}

	@Override
	def calculateResult2(fileName){
		List<String> lines = Util.readFile(fileName)
		int sum
		lines.each { line ->
			String[] digits = line.split(" ")
			Map<Integer, List<String>> lettersOfDigits = new HashMap<>()
			List<List> sixLines = []
			List<List> fiveLines = []
			
			def inputOutput = line.split(/[|]/)
			String inputs = inputOutput[0]
			String outputs = inputOutput[1]
			
			for(String digit : inputs.split(" ")) {
				List letters = digit.split("").collect().sort()
				if(digit.size() == 2) {
					lettersOfDigits.put(1, letters)
				}
				else if(digit.size() == 3) {
					lettersOfDigits.put(7, letters)
				}
				else if(digit.size() == 7) {
					lettersOfDigits.put(8, letters)
				}
				else if(digit.size() == 4) {
					lettersOfDigits.put(4, letters)
				}
				if(digit.size() == 5) {
					fiveLines << letters
				}
				if(digit.size() == 6) {
					sixLines << letters
				}
			}
			int zeroIdx, sixIdx
			sixLines.eachWithIndex { it, idx ->
				lettersOfDigits.get(1).each { oneLetter ->
					if(!it.contains(oneLetter)) {
						sixIdx = idx
					}
				}
			}
			lettersOfDigits.put(6, sixLines.get(sixIdx))
			sixLines.removeAt(sixIdx)
			
			sixLines.eachWithIndex { it, idx ->
				lettersOfDigits.get(4).each { letter ->
					if(!it.contains(letter)) {
						zeroIdx = idx
					}
				}
			}
			lettersOfDigits.put(0, sixLines.get(zeroIdx))
			sixLines.removeAt(zeroIdx)
			lettersOfDigits.put(9, sixLines.get(0))
			
			int twoIdx, threeIdx
			fiveLines.eachWithIndex { it, idx ->
				boolean good = true
				lettersOfDigits.get(1).each { oneLetter ->
					if(!it.contains(oneLetter)) {
						good = false
					}
				}
				if(good) threeIdx = idx
			}
			lettersOfDigits.put(3, fiveLines.get(threeIdx))
			fiveLines.remove(threeIdx)
			
			fiveLines.eachWithIndex { it, idx ->
				(lettersOfDigits.get(4) - lettersOfDigits.get(1)).each { oneLetter ->
					if(!it.contains(oneLetter)) {
						twoIdx = idx
					}
				}
			}
			lettersOfDigits.put(2, fiveLines.getAt(twoIdx))
			fiveLines.removeAt(twoIdx)
			lettersOfDigits.put(5, fiveLines.get(0))
			
			int i = 1000
			for(String digit : outputs.trim().split(" ")) {
				def sorted = digit.split("").collect().sort().join("")
				lettersOfDigits.find {k, v ->
					if(v.join("").equals(sorted)) {
						sum += k * i
						return true
					}
					false
				}
				i/=10
			}
		}
		sum
	}
}
