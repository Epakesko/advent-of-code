package com.epakesko.advent.of.code.aoc_2021.day10;

import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day
import java.awt.Point

public class Day10 extends Day{	
	@Override
	def calculateResult(fileName){
		List<String> lines = Util.readFile(fileName)
		long sum
		lines.each { line ->
			List<String> openingChars = new ArrayList<>()
			for(String c : line.split("")) {
				if(c.matches(/[\<\{\[\(]/)) openingChars << c
				else if(c.matches(/[\>]/)) {
					if(!openingChars.last().equals("<")) {
						sum += 25137
						break
					}
					else {
						openingChars.removeLast()
					}
				}
				else if(c.matches(/[\]]/)) {
					if(!openingChars.last().equals("[")) {
						sum += 57
						break
					}
					else {
						openingChars.removeLast()
					}
				}
				else if(c.matches(/[\)]/)) {
					if(!openingChars.last().equals("(")) {
						sum += 3
						break
					}
					else {
						openingChars.removeLast()
					}
				}
				else if(c.matches(/[\}]/)) {
					if(!openingChars.last().equals("{")) {
						sum += 1197
						break
					}
					else {
						openingChars.removeLast()
					}
				}
			}
		}
		sum
	}

	@Override
	def calculateResult2(fileName){
		List<String> lines = Util.readFile(fileName)
		List sums = []
		lines.each { line ->
			long sum
			List<String> openingChars = new ArrayList<>()
			boolean invalid = false
			for(String c : line.split("")) {
				if(c.matches(/[\<\{\[\(]/)) openingChars << c
				else if(c.matches(/[\>]/)) {
					if(!openingChars.last().equals("<")) {
						invalid = true
						break
					}
					else {
						openingChars.removeLast()
					}
				}
				else if(c.matches(/[\]]/)) {
					if(!openingChars.last().equals("[")) {
						invalid = true
						break
					}
					else {
						openingChars.removeLast()
					}
				}
				else if(c.matches(/[\)]/)) {
					if(!openingChars.last().equals("(")) {
						invalid = true
						break
					}
					else {
						openingChars.removeLast()
					}
				}
				else if(c.matches(/[\}]/)) {
					if(!openingChars.last().equals("{")) {
						invalid = true
						break
					}
					else {
						openingChars.removeLast()
					}
				}
			}
			if(!invalid) {
				openingChars.reverse().each { String c ->
					sum *= 5
					if(c.matches(/[\<]/)) {
						sum += 4
					}
					else if(c.matches(/[\[]/)) {
						sum += 2
					}
					else if(c.matches(/[\(]/)) {
						sum += 1
					}
					else if(c.matches(/[\{]/)) {
						sum += 3
					}
				}
				sums << sum
			}
		}
		sums.sort()[(sums.size() - 1) / 2]
	}
}
