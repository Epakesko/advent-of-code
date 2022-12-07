package aoc2020.day01;

import aoc2020.Day

public class Day01 extends Day{
	
	@Override
	void test() {
		if(514579 == calculateResult("aoc2020/day01/testinput.txt")) println "success"
		else println "fail"
	}
	
	@Override
	def calculateResult(fileName){
		File file = new File(fileName)
		println file.getAbsolutePath()
		def intList = file.readLines()
		
		for(int i = 0; i < intList.size; i++) {
			for(int j = i+1; j < intList.size; j++) {
				def sum = (intList[i] as Integer) + (intList[j] as Integer)
				if(sum == 2020)	return (intList[i] as Integer) * (intList[j] as Integer)
			}
		}
		
		return -1
	}
	
	@Override
	def calculateResult2(fileName){
		File file = new File(fileName)
		println file.getAbsolutePath()
		def intList = file.readLines()
		
		for(int i = 0; i < intList.size; i++) {
			for(int j = i+1; j < intList.size; j++) {
				for(int k = j+1; k < intList.size; k++) {
					def sum = (intList[i] as Integer) + (intList[j] as Integer) + (intList[k] as Integer)
					if(sum == 2020)	return (intList[i] as Integer) * (intList[j] as Integer)* (intList[k] as Integer)
				}
			}
		}
		
		return -1
	}
}
