package com.epakesko.advent.of.code.aoc_2021.day04;

import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day

public class Day04 extends Day{
	@Override
	def calculateResult(fileName){
		List<String> lines = Util.readFile(fileName)
		List<Integer> numbers = lines[0].split(",").collect{ it as Integer }
		
		int fastest = Integer.MAX_VALUE
		int fastestResult = -1
		
		for(int i = 0; i < (lines.size()-1)/6; i++) {
			List rows = []
			List columns = [[],[],[],[],[]]
			List nums = []
			
			int sum = 0
			for(int j = 0; j < 5; j++) {
				def splittedRow = lines[i * 6 + j + 2].trim().split(/[ ]+/).collect{it as Integer}
				rows << splittedRow
				splittedRow.eachWithIndex { num, idx ->
					nums << num
					columns[idx] << num
				}
				sum += splittedRow.sum()
			}
			int minMax = Integer.MAX_VALUE
			int lastNum = -1
			rows.each { List row ->
				int rowMax = row.max{ numbers.indexOf(it) }
			    int idx = numbers.indexOf(rowMax)
				if(minMax > idx) {
					minMax = idx
					lastNum = rowMax
				} 
			}
			columns.each { List column ->
				int columnMax = column.max{ numbers.indexOf(it) }
			    int idx = numbers.indexOf(columnMax)
				if(minMax > idx) {
					minMax = idx
					lastNum = columnMax
				} 
			}
			if(fastest > minMax) {
				fastest = minMax
				
				for(int j = 0; j <= minMax; j ++) {
					int num = numbers[j]
					if(nums.contains(num)) sum -= num
				}
				
				fastestResult = sum * lastNum
			}
			
		}
		fastestResult
	}
		
	@Override
	def calculateResult2(fileName){
				List<String> lines = Util.readFile(fileName)
		List<Integer> numbers = lines[0].split(",").collect{ it as Integer }
		
		int slowest = -1
		int slowestResult = -1
		
		for(int i = 0; i < (lines.size()-1)/6; i++) {
			List rows = []
			List columns = [[],[],[],[],[]]
			List nums = []
			
			int sum = 0
			for(int j = 0; j < 5; j++) {
				def splittedRow = lines[i * 6 + j + 2].trim().split(/[ ]+/).collect{it as Integer}
				rows << splittedRow
				splittedRow.eachWithIndex { num, idx ->
					nums << num
					columns[idx] << num
				}
				sum += splittedRow.sum()
			}
			int minMax = Integer.MAX_VALUE
			int lastNum = -1
			rows.each { List row ->
				int rowMax = row.max{ numbers.indexOf(it) }
			    int idx = numbers.indexOf(rowMax)
				if(minMax > idx) {
					minMax = idx
					lastNum = rowMax
				} 
			}
			columns.each { List column ->
				int columnMax = column.max{ numbers.indexOf(it) }
			    int idx = numbers.indexOf(columnMax)
				if(minMax > idx) {
					minMax = idx
					lastNum = columnMax
				} 
			}
			if(slowest < minMax) {
				slowest = minMax
				
				for(int j = 0; j <= minMax; j ++) {
					int num = numbers[j]
					if(nums.contains(num)) sum -= num
				}
				
				slowestResult = sum * lastNum
			}
			
		}
		slowestResult
	}
}
