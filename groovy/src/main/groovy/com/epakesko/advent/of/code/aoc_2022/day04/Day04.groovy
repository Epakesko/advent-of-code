package com.epakesko.advent.of.code.aoc_2022.day04;

import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day

public class Day04 extends Day{	
	@Override
	def calculateResult(fileName){
		List lines = Util.readFile(fileName)
		lines.count { String line ->
			String[] splitted = line.split(",")
			int[] elf1 = splitted[0].split("-").collect { it as Integer }
			int[] elf2 = splitted[1].split("-").collect { it as Integer }
			return !((elf1[0] > elf2[0] && elf1[1] > elf2[1]) || (elf1[0] < elf2[0] && elf1[1] < elf2[1]))
		}
	}
	
	@Override
	def calculateResult2(fileName){
		List lines = Util.readFile(fileName)
		lines.count { String line ->
			String[] splitted = line.split(",")
			int[] elf1 = splitted[0].split("-").collect { it as Integer }
			int[] elf2 = splitted[1].split("-").collect { it as Integer }
			return !(elf1[0] > elf2[1] || elf1[1] < elf2[0])
		}
	}
}
