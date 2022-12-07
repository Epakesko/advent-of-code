package com.epakesko.advent.of.code.aoc_2021.day03;

import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day

public class Day03 extends Day{
	@Override
	def calculateResult(fileName){
		List<String> bitList = Util.readFile(fileName)
		List sumOfBits = []
		int g, e
		bitList[0].size().times{ 
			sumOfBits << bitList.sum { bits -> 
				(bits.charAt(it) as Integer) - 48
			}
		}
		int j = 0
		for(def c : sumOfBits.reverse()) {
			if((c as Integer) < bitList.size() / 2) {
				g += Math.pow(2, j++)
			}
			else {
				e += Math.pow(2, j++)
			}
		}
		g * e
	}
	
	@Override
	def calculateResult2(fileName){
		List<String> bitList = Util.readFile(fileName)
		Set<String> O2 = []
		Set<String> CO2 = []
		bitList.each { bits ->
			CO2 << bits
			O2 << bits
		}
		int i = 0
		while(O2.size() > 1) {
			def sum = O2.sum { o2bits ->
				(o2bits.charAt(i) as Integer) - 48
			}
			if(sum < O2.size() / 2) {
				O2 = O2.findAll{ o2bits -> (o2bits.charAt(i) as Integer) == 48 }
			}
			else {
				O2 = O2.findAll{ o2bits -> (o2bits.charAt(i) as Integer) == 49 }
			}
			i++
		}
		int j = 0
		while(CO2.size() > 1) {
			def sum = CO2.sum { co2bits ->
				(co2bits.charAt(j) as Integer) - 48
			}
			if(sum < CO2.size() / 2) {
				CO2 = CO2.findAll{ co2bits -> (co2bits.charAt(j) as Integer) == 49 }
			}
			else {
				CO2 = CO2.findAll{ co2bits -> (co2bits.charAt(j) as Integer) == 48 }
			}
			j++
		}
		Integer.parseInt(O2[0], 2) * Integer.parseInt(CO2[0], 2)
	}
}
