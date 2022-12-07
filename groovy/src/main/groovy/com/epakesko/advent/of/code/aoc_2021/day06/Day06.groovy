package com.epakesko.advent.of.code.aoc_2021.day06;

import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day

public class Day06 extends Day{	
	@Override
	def calculateResult(fileName){
		List<String> lines = Util.readFile(fileName)
		Map<Integer, Integer> numberOfFishWithAge = new HashMap<>()
		
		9.times {
			numberOfFishWithAge.put(it, 0)
		}
		
		int sum = 0
		lines[0].split(",").collect{it as Integer}.each {
			numberOfFishWithAge.put(it, numberOfFishWithAge.get(it) + 1)
			sum++
		}
		
		for(int i = 0; i < 80; i++) {
			int wasZero = numberOfFishWithAge.get(0)
			numberOfFishWithAge.put(0, numberOfFishWithAge.get(1))
			numberOfFishWithAge.put(1, numberOfFishWithAge.get(2))
			numberOfFishWithAge.put(2, numberOfFishWithAge.get(3))
			numberOfFishWithAge.put(3, numberOfFishWithAge.get(4))
			numberOfFishWithAge.put(4, numberOfFishWithAge.get(5))
			numberOfFishWithAge.put(5, numberOfFishWithAge.get(6))
			numberOfFishWithAge.put(6, wasZero + numberOfFishWithAge.get(7))
			numberOfFishWithAge.put(7, numberOfFishWithAge.get(8))
			numberOfFishWithAge.put(8, wasZero)
			sum += wasZero
		}
		sum
	}

	@Override
	def calculateResult2(fileName){
		List<String> lines = Util.readFile(fileName)
		Map<Integer, BigInteger> numberOfFishWithAge = new HashMap<>()
		
		9.times {
			numberOfFishWithAge.put(it, new BigInteger(0))
		}
		
		BigInteger sum = 0
		lines[0].split(",").collect{it as Integer}.each {
			numberOfFishWithAge.put(it, numberOfFishWithAge.get(it) + 1)
			sum++
		}
		
		for(int i = 0; i < 256; i++) {
			BigInteger wasZero = numberOfFishWithAge.get(0)
			numberOfFishWithAge.put(0, numberOfFishWithAge.get(1))
			numberOfFishWithAge.put(1, numberOfFishWithAge.get(2))
			numberOfFishWithAge.put(2, numberOfFishWithAge.get(3))
			numberOfFishWithAge.put(3, numberOfFishWithAge.get(4))
			numberOfFishWithAge.put(4, numberOfFishWithAge.get(5))
			numberOfFishWithAge.put(5, numberOfFishWithAge.get(6))
			numberOfFishWithAge.put(6, wasZero + numberOfFishWithAge.get(7))
			numberOfFishWithAge.put(7, numberOfFishWithAge.get(8))
			numberOfFishWithAge.put(8, wasZero)
			sum += wasZero
		}
		sum
	}
}
