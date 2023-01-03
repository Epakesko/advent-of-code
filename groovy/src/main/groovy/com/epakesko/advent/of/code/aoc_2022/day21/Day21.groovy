package com.epakesko.advent.of.code.aoc_2022.day21;

import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day

public class Day21 extends Day{
	
	@Override
	def calculateResult(fileName){
		List<String> lines = Util.readFile(fileName)
		Map<String, String> yelling = new HashMap<>()
		lines.each {
			def splitted = it.split(": ")
			yelling.put(splitted[0], splitted[1])
		}
		getYelledValue("root", yelling)
	}
	
	long getYelledValue(String monke, Map<String, String> yelling) {
		String yell = yelling.get(monke)
		if(yell.contains(" ")) {
			def splitted = yell.split(" ")
			
			long yelledValue = getYelledValue(splitted[0], yelling)
			long value2 = getYelledValue(splitted[2], yelling)
			switch(splitted[1]) {
				case "*":
					yelledValue *= value2
					break
				case "+":
					yelledValue += value2
					break
				case "-":
					yelledValue -= value2
					break
				case "/":
					yelledValue /= value2
					break
			}
			yelling.put(monke, "$yelledValue")
			return yelledValue
		}
		else {
			return (yell as Integer)
		}
	}
	
	String getEquation(String monke, Map<String, String> yelling) {
		String yell = yelling.get(monke)
		if(monke.equals("humn")) return "humn"
		else if(yell.contains(" ")) {
			def splitted = yell.split(" ")
			String yellPart1 = getEquation(splitted[0], yelling)
			String yellPart2 = getEquation(splitted[2], yelling)
			if(monke.equals("root")) splitted[1] = "=="
			if(yellPart1.contains("humn") || yellPart2.contains("humn")) return "($yellPart1 ${splitted[1]} $yellPart2)"
			long yelledValue = yellPart1 as Long
			long value2 = yellPart2 as Long
			switch(splitted[1]) {
				case "*":
					yelledValue *= value2
					break
				case "+":
					yelledValue += value2
					break
				case "-":
					yelledValue -= value2
					break
				case "/":
					yelledValue /= value2
					break
			}
			yelling.put(monke, "$yelledValue")
			return yelledValue
		}
		else return yell
	}
	
	@Override
	def calculateResult2(fileName) {
		List<String> lines = Util.readFile(fileName)
		Map<String, String> yelling = new HashMap<>()
		lines.each {
			def splitted = it.split(": ")
			yelling.put(splitted[0], splitted[1])
		}
		String equation = getEquation("root", yelling)
		equation
	}
}
