package com.epakesko.advent.of.code.aoc_2019.Day16

import com.epakesko.advent.of.code.aoc_2019.util.IntCode
import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day
import java.awt.Point

class Day16 extends Day {
	
	@Override
	public Object calculateResult(Object fileName) {	
		String input = Util.readFile(fileName)[0]
		List inputNumbers = input.split("").collect { it as Integer }
		
		100.times {
			List outputNumbers = []
			inputNumbers.size().times { inputIdx ->
				List patternNumbers = []
				(inputIdx+1).times { patternNumbers << 0 }
				(inputIdx+1).times { patternNumbers << 1 }
				(inputIdx+1).times { patternNumbers << 0 }
				(inputIdx+1).times { patternNumbers << -1 }
				int sum = 0;
				
				inputNumbers.eachWithIndex { number, idx ->
					sum += number * patternNumbers[(idx+1) % patternNumbers.size()]
				}
				outputNumbers << Math.abs(sum) % 10
			}
			inputNumbers = outputNumbers
			outputNumbers = []
		}
		
		inputNumbers[0..7].join("")
	}
	
	@Override
	public Object calculateResult2(Object fileName) {
		String input = Util.readFile(fileName)[0]
		List inputList = input.split("")
		int offset = input.substring(0, 7) as Integer
		List realInputList = []
		(inputList.size() * 10000 - offset).times { idx ->
			realInputList << inputList[(offset + idx) % inputList.size()] 
		}
		100.times {
			List outputNumbers = []
			int sum = 0
			realInputList.size().times { inputIdx ->
				int nthLast = realInputList[-(inputIdx + 1)]
				sum += nthLast
				outputNumbers << Math.abs(sum) % 10
			}
			realInputList = outputNumbers.reverse()
			outputNumbers = []
		}
		
		realInputList[0..7].join("")
	}
}
