package aoc2020.day16

import aoc2020.Day
import aoc2020.common.Util

class Day16 extends Day {

	@Override
	public Object calculateResult(Object fileName) {
		List lines = Util.readFile(fileName)
		Map rules = [:]
		
		int i = 0
		for(i; i < lines.size(); i++) {
			String line = lines[i]
			if(line.trim().equals("")) {
				i++
				break
			}
			Map parsedRule = parseRule(line)
			rules << parseRule(line)
		}
		
		for(i; i < lines.size(); i++) {
			String line = lines[i]
			if(line.trim().equals("")) {
				i++
				break
			}
		}
		int sum = 0
		for(i; i < lines.size(); i++) {
			String line = lines[i]
			if(line.trim().equals("nearby tickets:")) continue
			List nums = line.split(",").collect {it as Integer}
			
			
			nums.each { num ->
				boolean validNum = false
				
				rules.each { k, v ->
					v.each { rule ->
						if(num <= rule.max && num >= rule.min) {
							validNum = true
						}
						if(validNum) return
					}
					if(validNum) return
				}
				
				if(!validNum) {
					sum += num
					return
				}
			}
		}
		
		sum
	}

	Map parseRule(String ruleLine) {
		def (ruleName, ruleParams) = ruleLine.split(": ")
		def (ruleParams1, ruleParams2) = ruleParams.split(" or ")
		def (ruleParams1Min, ruleParams1Max) = ruleParams1.split("-")
		def (ruleParams2Min, ruleParams2Max) = ruleParams2.split("-")
		[(ruleName): [[min: ruleParams1Min as Integer, max: ruleParams1Max as Integer], [min: ruleParams2Min as Integer, max: ruleParams2Max as Integer]]]
	}
	
	@Override
	public Object calculateResult2(Object fileName) {
		List lines = Util.readFile(fileName)
		Map rules = [:]
		List myTicket
		
		int i = 0
		for(i; i < lines.size(); i++) {
			String line = lines[i]
			if(line.trim().equals("")) {
				i++
				break
			}
			Map parsedRule = parseRule(line)
			rules << parseRule(line)
		}
		
		for(i; i < lines.size(); i++) {
			String line = lines[i]
			if(line.trim().equals("your ticket:")) continue
			if(line.trim().equals("")) {
				i++
				break
			}
			myTicket = line.split(",").collect {it as Integer}
		}
		long product = 1
		Map possibilities = [:]
		for(i; i < lines.size(); i++) {
			String line = lines[i]
			if(line.trim().equals("nearby tickets:")) continue
			List nums = line.split(",").collect {it as Integer}
			
			nums.eachWithIndex { num, idx ->
				boolean validNum = false
				Set possibilitesSet = []
				rules.each { k, v ->
					v.each { rule ->
						if(num <= rule.max && num >= rule.min) {
							validNum = true
							possibilitesSet << k
						}
					}
					if(validNum) return
				}
				
				if(!validNum) {
					return
				}
				else {
					if(possibilities[idx] == null) possibilities[idx] = possibilitesSet
					else possibilities[idx] = possibilities[idx].intersect(possibilitesSet)
				}
			}
		}
		
		while(true) {
			for(Map.Entry entry : possibilities) {
				if(entry.value.size() == 1) {
					for(Map.Entry entry2 : possibilities) {
						if(entry2.value.size() != 1) entry2.value -= entry.value[0]
					}
				}
			}
			
			if(possibilities.values().find { it.size() > 1 } == null) break
		}
		
		for(Map.Entry entry : possibilities) {
			if(entry.value[0].startsWith("departure")){
				product *= myTicket[entry.key]
			}
		}
		
		product
	}
}
