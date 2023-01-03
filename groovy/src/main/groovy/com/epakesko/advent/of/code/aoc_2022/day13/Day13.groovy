package com.epakesko.advent.of.code.aoc_2022.day13;

import com.epakesko.advent.of.code.common.Util
import com.epakesko.advent.of.code.day.Day

import groovy.json.JsonSlurper

public class Day13 extends Day{
	
	int compareLists(List l1, List l2) {
		for(int i = 0; i < l1.size(); i++) {
			if(i >= l2.size()) return 1
			if(l1[i] instanceof Integer) {
				if(l2[i] instanceof Integer) {
					if(l1[i] > l2[i]) return 1;
					else if(l1[i] < l2[i]) return -1
				}
				else {
					int result = compareLists([l1[i]], l2[i])
					if(result != 0) return result
				}
			}
			else {
				if(l2[i] instanceof Integer) {
					int result = compareLists(l1[i], [l2[i]])
					if(result != 0) return result
				}
				else {
					int result = compareLists(l1[i], l2[i])
					if(result != 0) return result
				}
			}
		}
		if(l1.size() < l2.size()) return -1
		else return 0
	}
	
	@Override
	def calculateResult(fileName){
		List<String> lines = Util.readFile(fileName)
		JsonSlurper slurper = new JsonSlurper()
		List<List> packetsList = new ArrayList<>()
		packetsList = lines.join("-").split("--").collect { it.split("-").toList().collect{ slurper.parseText(it)} }
		
		int sum = 0
		packetsList.eachWithIndex { List packets, idx ->
			int result = compareLists(packets[0], packets[1])
			if(result < 0) sum += idx + 1
		}
		sum
	}
	
	@Override
	def calculateResult2(fileName){
		List<String> lines = Util.readFile(fileName)
		JsonSlurper slurper = new JsonSlurper()
		List<List> packetsList = new ArrayList<>()
		packetsList = lines.findAll { it }.collect { slurper.parseText(it) }
		packetsList << [[2]]
		packetsList << [[6]]
		packetsList.sort { a, b -> compareLists(a, b) }
		
		(packetsList.findIndexOf { it.size() == 1 && it[0] instanceof List && it[0].size() == 1 && it[0][0] == 2 } + 1) *
		(packetsList.findIndexOf { it.size() == 1 && it[0] instanceof List && it[0].size() == 1 && it[0][0] == 6 } + 1)
	}
}
