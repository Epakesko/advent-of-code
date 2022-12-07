package aoc2020.day01;

import aoc2020.Day
import aoc2020.common.Util

public class Day01 extends Day{
	final static int SUM = 2020
	
	@Override
	def calculateResult(fileName){
		List intList = Util.readFileAsInts(fileName)
		
		for(int i = 0; i < intList.size(); i++) {
			for(int j = i+1; j < intList.size(); j++) {
				def sum = intList[i] + intList[j]
				if(sum == SUM)	return (intList[i] as Integer) * (intList[j] as Integer)
			}
		}
	}
	
	@Override
	def calculateResult2(fileName){
		List intList = Util.readFileAsInts(fileName)
		
		for(int i = 0; i < intList.size(); i++) {
			for(int j = i+1; j < intList.size(); j++) {
				for(int k = j+1; k < intList.size(); k++) {
					def sum = intList[i] + intList[j] + intList[k]
					if(sum == SUM)	return intList[i] * intList[j] * intList[k]
				}
			}
		}
	}
}
